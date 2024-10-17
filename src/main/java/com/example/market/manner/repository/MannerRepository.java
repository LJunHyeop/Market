package com.example.market.manner.repository;

import com.example.market.entity.Manner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MannerRepository extends JpaRepository<Manner, Long> {
}
