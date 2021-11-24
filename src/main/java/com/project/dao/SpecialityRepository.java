package com.project.dao;

import com.project.domain.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<Speciality,Integer> {

    List<Speciality> findByRecruitmentCompletedFalse();

    Optional<Speciality> findByTitle(String title);
}
