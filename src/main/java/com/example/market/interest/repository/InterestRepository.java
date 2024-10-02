package com.example.market.interest.repository;

import com.example.market.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {
                // User(Entity)_UserPk(column)
//    List<Interest> findByUser_UserPk(Long userPk);
}
