package com.teamvoy.testJava.repositories;

import com.teamvoy.testJava.models.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
