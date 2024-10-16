package com.example.market.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.market.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserEmail(String userEmail);

    User findByUserEmail(String userEmail);

    User findUserByUserPk(Long userPk);

    User findByUserEmailAndUserPhone(String userEmail ,String userPhone);

    User findByUserNameAndUserPhone(String userName, String userPhone);
}
