package com.project.dao;

import com.project.domain.Applicant;
import com.project.domain.Application;
import com.project.domain.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    List<Application> findByApplicant(Applicant applicant);

    Optional<Application> findByApplicantAndSpeciality(Applicant applicant, Speciality speciality);
}
