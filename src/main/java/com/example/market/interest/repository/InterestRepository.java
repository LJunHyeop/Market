package com.example.market.interest.repository;

import com.example.market.entity.Interest;
import com.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    List<Interest> findByUser_UserPk(Long userPk);
}
