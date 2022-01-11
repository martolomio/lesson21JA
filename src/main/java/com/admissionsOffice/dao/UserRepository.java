package com.admissionsOffice.dao;

import com.admissionsOffice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    User findByActivationCode(String code);
}
