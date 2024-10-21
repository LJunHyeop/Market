package com.example.market.location;


import com.example.market.entity.Location;
import com.example.market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByUserPk(User userPk);
}