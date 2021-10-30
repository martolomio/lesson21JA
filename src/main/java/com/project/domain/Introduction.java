package com.project.domain;

import java.util.Map;
import java.util.Objects;

public class Introduction {

    private Integer id;
    private Entrant entrant;
    private Speciality speciality;
    private Map<Subject,Integer> markOfExams;
    private Integer attMark;

    public Introduction() {
    }

    public Introduction(Entrant entrant,Speciality speciality,Map<Subject,Integer> markOfExams,Integer attMark) {
        this.entrant = entrant;
        this.speciality = speciality;
        this.markOfExams = markOfExams;
        this.attMark = attMark;

    }

    public Introduction(Integer id,Entrant entrant,Speciality speciality,Map<Subject,Integer> markOfExams,Integer attMark) {
        this.id = id;
        this.entrant = entrant;
        this.speciality = speciality;
        this.markOfExams = markOfExams;
        this.attMark = attMark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Entrant getEntrant() {
        return entrant;
    }

    public void setEntrant(Entrant entrant) {
        this.entrant = entrant;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Map<Subject, Integer> getMarkOfExams() {
        return markOfExams;
    }

    public void setMarkOfExams(Map<Subject, Integer> markOfExams) {
        this.markOfExams = markOfExams;
    }

    public Integer getAttMark() {
        return attMark;
    }

    public void setAttMark(Integer attMark) {
        this.attMark = attMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Introduction that = (Introduction) o;
        return Objects.equals(id, that.id) && Objects.equals(entrant, that.entrant) && Objects.equals(speciality, that.speciality) && Objects.equals(markOfExams, that.markOfExams) && Objects.equals(attMark, that.attMark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entrant, speciality, markOfExams, attMark);
    }

    @Override
    public String toString() {
        return "Introduction{" +
                "id=" + id +
                ", entrant=" + entrant +
                ", speciality=" + speciality +
                ", markOfExams=" + markOfExams +
                ", attMark=" + attMark +
                '}';
    }
}
