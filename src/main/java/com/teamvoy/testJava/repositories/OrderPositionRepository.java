package com.teamvoy.testJava.repositories;

import com.teamvoy.testJava.models.OrderPosition;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}
