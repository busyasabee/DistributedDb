package com.dmitrromashov;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;

    @Column(name = "day_of_birth")
    private Date dayOfBirth;
    private String amplya;
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country countryByCountryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getAmplya() {
        return amplya;
    }

    public void setAmplya(String amplya) {
        this.amplya = amplya;
    }


//    @ManyToOne
//    @JoinColumn(name = "country_id", referencedColumnName = "id")
    public Country getCountryByCountryId() {
        return countryByCountryId;
    }

    public void setCountryByCountryId(Country countryByCountryId) {
        this.countryByCountryId = countryByCountryId;
    }
}
