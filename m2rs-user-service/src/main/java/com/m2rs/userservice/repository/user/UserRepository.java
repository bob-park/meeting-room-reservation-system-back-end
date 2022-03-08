package com.m2rs.userservice.repository.user;

import com.m2rs.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
