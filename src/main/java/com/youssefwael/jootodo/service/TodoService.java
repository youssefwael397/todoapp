package com.youssefwael.jootodo.service;

import com.youssefwael.jootodo.dto.TodoRequestDto;
import com.youssefwael.jootodo.entity.Todo;
import com.youssefwael.jootodo.entity.User;
import com.youssefwael.jootodo.repository.TodoRepository;
import com.youssefwael.jootodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    public List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    // In TodoServiceImpl
    public boolean isOwnerByEmail(Long todoId, String email) {
        Todo todo = getTodoById(todoId);
        if (todo == null || todo.getUser() == null) {
            return false;
        }
        return todo.getUser().getEmail().equals(email);
    }

    public Todo saveTodoForUser(Todo todo, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        todo.setUser(user);
        return todoRepository.save(todo);
    }


    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long todoId, TodoRequestDto todoRequestDto) {
        return todoRepository.findById(todoId).map(existingTodo -> {
            // Use ModelMapper to copy properties from DTO to existing entity
            modelMapper.map(todoRequestDto, existingTodo);
            // Preserve certain fields that shouldn't be updated via DTO
            // (id, createdAt, user are typically preserved)
            return todoRepository.save(existingTodo);
        }).orElse(null);
    }

    public Todo toggleTodoCompletion(Long todoId) {
        return todoRepository.findById(todoId).map(existingTodo -> {
            existingTodo.setCompleted(!Boolean.TRUE.equals(existingTodo.getCompleted()));
            return todoRepository.save(existingTodo);
        }).orElse(null);
    }

    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}