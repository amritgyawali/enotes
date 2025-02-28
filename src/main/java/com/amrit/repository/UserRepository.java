package com.amrit.repository;

import com.amrit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);
}
