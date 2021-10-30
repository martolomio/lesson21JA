package com.project.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class Entrant {

    private LocalDate birthday;
    private String address;
    private String school;
    private Set<Speciality> specialities;
    private Set<Introduction> introductions;

    public Entrant(LocalDate birthday,String address, String school) {
        this.birthday = birthday;
        this.address = address;
        this.school = school;
    }

    public Entrant(LocalDate birthday,String address, String school,Set<Speciality> specialities,Set<Introduction> introductions) {
        this.birthday = birthday;
        this.address = address;
        this.school = school;
        this.specialities = specialities;
        this.introductions = introductions;
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

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
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
        Entrant entrant = (Entrant) o;
        return Objects.equals(birthday, entrant.birthday) && Objects.equals(address, entrant.address) && Objects.equals(school, entrant.school) && Objects.equals(specialities, entrant.specialities) && Objects.equals(introductions, entrant.introductions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, address, school, specialities, introductions);
    }

    @Override
    public String toString() {
        return "Entrant{" +
                "birthday=" + birthday +
                ", address='" + address + '\'' +
                ", school='" + school + '\'' +
                ", specialities=" + specialities +
                ", introductions=" + introductions +
                '}';
    }
}
