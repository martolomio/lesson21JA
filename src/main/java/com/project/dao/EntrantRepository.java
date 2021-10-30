package com.project.dao;

import com.project.domain.Entrant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrantRepository extends JpaRepository<Entrant,Integer> {
}
