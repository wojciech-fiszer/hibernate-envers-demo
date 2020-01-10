package com.fiserv.hibernateenversdemo.repository;

import com.fiserv.hibernateenversdemo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
