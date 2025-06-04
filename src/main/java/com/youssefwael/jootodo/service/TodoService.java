package com.youssefwael.jootodo.service;

import com.youssefwael.jootodo.entity.Todo;
import com.youssefwael.jootodo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }

    public Todo getTodoById(Long id){
        return todoRepository.findById(id).orElse(null);
    }

    public List<Todo> getTodosByUserId(Long userId){
        return todoRepository.findByUser_IdOrderByCreatedAtDesc(userId);
    }
}
