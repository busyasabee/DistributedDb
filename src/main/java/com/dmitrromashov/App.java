package com.dmitrromashov;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.sql.Connection;
import java.util.Date;

public class App
{
    private static SessionFactory sessionFactory;
    private static Connection connection;

    public static void main( String[] args )
    {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            Metadata metadata = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(Country.class)
                    .buildMetadata();
            System.out.println("Hello");
            sessionFactory = metadata.getSessionFactoryBuilder().build();
            connection = null;//sessionFactory.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();

//            Configuration configuration = new Configuration()
//                    .addAnnotatedClass(Country.class)
//                    .setProperty("hibernate.dialect","org.hibernate.dialect.MySQL57Dialect")
//                    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
//                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost/distribute?serverTimezone=UTC&roundRobinLoadBalance=true")
//                    .setProperty("hibernate.connection.username", "root")
//                    .setProperty("hibernate.connection.password","123")
//                    .setProperty("hibernate.show_sql", "true")
//                    .setProperty("hibernate.hbm2ddl.auto", "validate");
//            sessionFactory = configuration.
//                    buildSessionFactory();
        } catch (Exception e){
            System.err.println("Failed to create sessionFactory object." + e);
            throw new ExceptionInInitializerError(e);
        }
        App app = new App();
        String countryName = "England";
        //Country country = app.addCountry(countryName);
        Country country2 = app.readCountry(1);
        System.out.println("Read country name " + country2.getName());
//        app.addCoach("Zinedin", "Zidan", country, new Date(1972, 6, 23),)
        sessionFactory.close();
    }

    public Country readCountry(int countryId){
        Session session = null;
        Transaction tx = null;
        Country country = new Country();

        try {
            //Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setReadOnly(true);
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            country = session.get(Country.class, countryId);
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

    public Country addCountry(String countryName){
        Session session = null;
//        session.setDefaultReadOnly(true); // не то
        Transaction tx = null;
        Integer countryId = null;
        Country country = new Country();

        try {
            Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
//            connection.setReadOnly(true);
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            country.setName(countryName);
            countryId = (Integer) session.save(country);
            System.out.println("Country " + countryName + " id = " + countryId);
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

    public Integer addCoach(String coachName, String coachSurname, Country coachCountry, Date coachDayOfBirth, Country country){

        Session session = null;
        Transaction tx = null;
        Integer coachId = null;

        try {
            Connection connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setReadOnly(true);
            session = sessionFactory.openSession();
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
