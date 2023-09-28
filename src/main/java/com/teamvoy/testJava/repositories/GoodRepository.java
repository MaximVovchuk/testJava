package com.teamvoy.testJava.repositories;

import com.teamvoy.testJava.models.Good;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface GoodRepository extends JpaRepository<Good, Long> {
    Optional<Good> findByNameAndPrice(String name, double price);
}
