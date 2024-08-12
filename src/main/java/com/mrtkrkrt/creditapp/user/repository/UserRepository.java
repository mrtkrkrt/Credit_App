package com.mrtkrkrt.creditapp.user.repository;

import com.mrtkrkrt.creditapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
