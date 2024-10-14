package com.example.market.user_assessment.repository;

import com.example.market.entity.User;
import com.example.market.entity.UserAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
