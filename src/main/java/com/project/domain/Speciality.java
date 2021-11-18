package com.project.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "speciality")
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speciality_id")
    private Integer id;
    @Column
    private String title;
    @Column
    private Integer numberStudentOn;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @ManyToMany(mappedBy = "entrantSpeciality")
    private Set<Entrant> entrants;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="speciality" )
    @Column(nullable = false)
    private Set<Introduction> introductions;

    public Speciality() {
    }

    public Speciality(String title) {
        this.title = title;
    }

    public Speciality(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberStudentOn() {
        return numberStudentOn;
    }

    public void setNumberStudentOn(Integer numberStudentOn) {
        this.numberStudentOn = numberStudentOn;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Set<Entrant> getEntrants() {
        return entrants;
    }

    public void setEntrants(Set<Entrant> entrants) {
        this.entrants = entrants;
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
        Speciality that = (Speciality) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(numberStudentOn, that.numberStudentOn) && Objects.equals(faculty, that.faculty) && Objects.equals(entrants, that.entrants) && Objects.equals(introductions, that.introductions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, numberStudentOn, faculty, entrants, introductions);
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", numberStudentOn=" + numberStudentOn +
                ", faculty=" + faculty +
                ", entrants=" + entrants +
                ", introductions=" + introductions +
                '}';
    }
}
