package com.project.domain;

import java.util.Objects;
import java.util.Set;

public class Faculty {

    private Integer id;
    private String title;
    private Set<Subject> examSub;
    private Set<Speciality> specialities;

    public Faculty() {
    }

    public Faculty(Integer id,String title,Set<Subject> examSub,Set<Speciality> specialities) {
        this.id = id;
        this.title = title;
        this.examSub = examSub;
        this.specialities = specialities;
    }

    public Faculty(String title,Set<Subject> examSub,Set<Speciality> specialities) {
        this.title = title;
        this.examSub = examSub;
        this.specialities = specialities;
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

    public Set<Subject> getExamSub() {
        return examSub;
    }

    public void setExamSub(Set<Subject> examSub) {
        this.examSub = examSub;
    }

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id) && Objects.equals(title, faculty.title) && Objects.equals(examSub, faculty.examSub) && Objects.equals(specialities, faculty.specialities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, examSub, specialities);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", examSub=" + examSub +
                ", specialities=" + specialities +
                '}';
    }
}
