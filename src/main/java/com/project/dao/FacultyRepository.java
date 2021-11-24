package com.project.dao;

import com.project.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty,Integer> {
    Optional<Faculty> findByTitle(String title);
}
