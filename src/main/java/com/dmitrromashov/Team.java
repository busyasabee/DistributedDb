package com.dmitrromashov;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Team {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    private String name;
    private String city;
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country countryByCountryId;
    @OneToOne
    @JoinColumn(name = "coach_id")
    private Coach coachByCoachId;


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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


//    @ManyToOne
//    @JoinColumn(name = "country_id", referencedColumnName = "id")
    public Country getCountryByCountryId() {
        return countryByCountryId;
    }

    public void setCountryByCountryId(Country countryByCountryId) {
        this.countryByCountryId = countryByCountryId;
    }

//    @ManyToOne
//    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    public Coach getCoachByCoachId() {
        return coachByCoachId;
    }

    public void setCoachByCoachId(Coach coachByCoachId) {
        this.coachByCoachId = coachByCoachId;
    }
}
