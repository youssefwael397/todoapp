package com.youssefwael.jootodo.repository;

import com.youssefwael.jootodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser_IdOrderByCreatedAtDesc(Long userId);
}
