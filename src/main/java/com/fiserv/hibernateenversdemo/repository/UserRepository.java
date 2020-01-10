package com.fiserv.hibernateenversdemo.repository;

import com.fiserv.hibernateenversdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
