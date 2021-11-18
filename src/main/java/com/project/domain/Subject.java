package com.project.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer id;
    @Column
    private String title;
    @ManyToMany(mappedBy = "examSub")
    private Set<Faculty> faculties;

    public Subject() {
    }

    public Subject(String title,Set<Faculty> faculties) {
        this.title = title;
        this.faculties = faculties;
    }
    public Subject(Integer id,String title,Set<Faculty> faculties) {
        this.id = id;
        this.title = title;
        this.faculties = faculties;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(title, subject.title) && Objects.equals(faculties, subject.faculties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, faculties);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", faculties=" + faculties +
                '}';
    }
}
