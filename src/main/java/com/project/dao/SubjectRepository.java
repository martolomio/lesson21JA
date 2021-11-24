package com.project.dao;

import com.project.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    Optional<Subject> findByTitle(String title);
}
