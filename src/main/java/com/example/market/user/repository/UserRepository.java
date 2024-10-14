package com.example.market.user.repository;

import com.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserEmail(String userEmail);

    User findByUserEmail(String userEmail);
}
