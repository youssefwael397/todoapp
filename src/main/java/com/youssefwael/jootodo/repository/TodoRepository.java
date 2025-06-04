package com.youssefwael.jootodo.repository;

import com.youssefwael.jootodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Todo findByTitle(String title);
    List<Todo> findByUser_IdOrderByCreatedAtDesc(Long userId);
}
