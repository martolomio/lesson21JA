package com.admissionsOffice.dao;

import com.admissionsOffice.domain.Applicant;
import com.admissionsOffice.domain.Application;
import com.admissionsOffice.domain.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    List<Application> findByApplicant(Applicant applicant);

    Optional<Application> findByApplicantAndSpeciality(Applicant applicant, Speciality speciality);
}
