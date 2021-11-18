package com.project.domain;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Double totalMark;
    @Column
    private boolean accepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rating() {
    }

    public Rating(Double totalMark,boolean accepted) {
        this.totalMark = totalMark;
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Double totalMark) {
        this.totalMark = totalMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return accepted == rating.accepted && Objects.equals(totalMark, rating.totalMark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalMark, accepted);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "totalMark=" + totalMark +
                ", accepted=" + accepted +
                '}';
    }
}
