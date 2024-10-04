package com.example.market.user_assessment.repository;

import com.example.market.entity.Manner;
import com.example.market.entity.UserAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAssessmentRepository extends JpaRepository<UserAssessment, Long> {
    public List<UserAssessment> findByUser_userPk(Long userPk);
}
