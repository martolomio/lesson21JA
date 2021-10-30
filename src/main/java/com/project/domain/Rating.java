package com.project.domain;

import java.util.Objects;

public class Rating {

    private Double totalMark;
    private boolean accepted;

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