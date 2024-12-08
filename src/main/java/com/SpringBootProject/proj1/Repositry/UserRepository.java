package com.SpringBootProject.proj1.Repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootProject.enums.userRole;
import com.SpringBootProject.proj1.Entitys.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findFirstByEmail(String username);
    User findByRole(userRole role);
}
