package com.admissionsOffice.dao;

import com.admissionsOffice.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty,Integer> {
    Optional<Faculty> findByTitle(String title);
}
