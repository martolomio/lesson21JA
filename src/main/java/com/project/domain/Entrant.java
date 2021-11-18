package com.project.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "entrant")
public class Entrant extends User {

    @Column
    private LocalDate birthday;
    @Column
    private String address;
    @Column
    private String school;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "speciality_entrant", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns =@JoinColumn(name = "speciality_id") )
    private Set<Speciality> entrantSpeciality;
    @Transient
    @MapsId
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entrant")
    @Column(nullable = false)
    private Set<Introduction> introductions;

    public Entrant(LocalDate birthday,String address, String school) {
        this.birthday = birthday;
        this.address = address;
        this.school = school;
    }

    public Entrant(LocalDate birthday,String address, String school,Set<Speciality> entrantSpeciality,Set<Introduction> introductions) {
        this.birthday = birthday;
        this.address = address;
        this.school = school;
        this.entrantSpeciality = entrantSpeciality;
        this.introductions = introductions;
    }

    public Entrant() {

    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Set<Speciality> getEntrantSpeciality() {
        return entrantSpeciality;
    }

    public void setEntrantSpeciality(Set<Speciality> entrantSpeciality) {
        this.entrantSpeciality = entrantSpeciality;
    }

    public Set<Introduction> getIntroductions() {
        return introductions;
    }

    public void setIntroductions(Set<Introduction> introductions) {
        this.introductions = introductions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Entrant entrant = (Entrant) o;
        return Objects.equals(birthday, entrant.birthday) && Objects.equals(address, entrant.address) && Objects.equals(school, entrant.school) && Objects.equals(entrantSpeciality, entrant.entrantSpeciality) && Objects.equals(introductions, entrant.introductions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), birthday, address, school, entrantSpeciality, introductions);
    }

    @Override
    public String toString() {
        return "Entrant{" +
                "birthday=" + birthday +
                ", address='" + address + '\'' +
                ", school='" + school + '\'' +
                ", entrantSpeciality=" + entrantSpeciality +
                ", introductions=" + introductions +
                '}';
    }
}
