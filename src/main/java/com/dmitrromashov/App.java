package com.dmitrromashov;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.internal.SessionImpl;

import java.sql.Connection;
import java.util.Date;

public class App
{
    private static SessionFactory sessionFactory;

    public static void main( String[] args )
    {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            Metadata metadata = new MetadataSources(standardRegistry)
                    .buildMetadata();

            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Exception e){
            System.err.println("Failed to create sessionFactory object." + e);
            throw new ExceptionInInitializerError(e);
        }


        App app = new App();
        String countryName = "England";
        app.addCountry(countryName);
        Country country = app.readCountry(1);
        sessionFactory.close();
    }

    public Country readCountry(int countryId){
        Session session = null;
        Transaction tx = null;
        Country country = new Country();

        try {
            session = sessionFactory.openSession();
            Connection connection = ((SessionImpl)session).connection();
            connection.setReadOnly(true);
            tx = session.beginTransaction();
            country = session.get(Country.class, countryId);
            System.out.println("Readed country name = " + country.getName());
            tx.commit();
            connection.close();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return country;
    }

    public void addCountry(String countryName){
        Session session = null;
        Transaction tx = null;
        Integer countryId = null;
        Country country = new Country();

        try {
            session = sessionFactory.openSession();
            Connection connection = ((SessionImpl)session).connection();
            connection.setReadOnly(false);
            tx = session.beginTransaction();

            country.setName(countryName);
            countryId = (Integer) session.save(country);
            System.out.println("Country " + countryName + " id = " + countryId + " added") ;
            tx.commit();
            connection.close();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Integer addCoach(String coachName, String coachSurname, Country coachCountry, Date coachDayOfBirth, Country country){

        Session session = null;
        Transaction tx = null;
        Integer coachId = null;

        try {
            session = sessionFactory.openSession();
            Connection connection = ((SessionImpl)session).connection();
            connection.setReadOnly(false);
            tx = session.beginTransaction();
            Coach coach = new Coach();
            coach.setName(coachName);
            coach.setSurname(coachSurname);
            coach.setDayOfBirth(coachDayOfBirth);
            coach.setCountry(country);
            coachId = (Integer) session.save(coach);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return coachId;
    }
}
