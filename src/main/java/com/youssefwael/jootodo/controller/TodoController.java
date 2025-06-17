package com.youssefwael.jootodo.controller;

import com.youssefwael.jootodo.dto.TodoRequestDto;
import com.youssefwael.jootodo.dto.TodoResponseDto;
import com.youssefwael.jootodo.dto.user.UserDto;
import com.youssefwael.jootodo.entity.Todo;
import com.youssefwael.jootodo.service.TodoService;
import com.youssefwael.jootodo.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        List<TodoResponseDto> dtoList = todos.stream()
                .map(todo -> modelMapper.map(todo, TodoResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/my-todos")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TodoResponseDto>> getMyTodos(Authentication authentication) {
        String email = authentication.getName();
        UserDto currentUser = userServiceImpl.getUserByEmail(email);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Todo> todos = todoService.getTodosByUserId(currentUser.getId());
        List<TodoResponseDto> dtoList = todos.stream()
                .map(todo -> modelMapper.map(todo, TodoResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{todoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId) {
        Todo todo = todoService.getTodoById(todoId);
        if (todo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Todo not found"));
        }
        TodoResponseDto responseDto = modelMapper.map(todo, TodoResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createTodo(@Valid @RequestBody TodoRequestDto todoRequestDto, Authentication authentication) {
        String email = authentication.getName();
        UserDto currentUser = userServiceImpl.getUserByEmail(email);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Todo todo = modelMapper.map(todoRequestDto, Todo.class);
        todo.setCompleted(false);
        todo.setCreatedAt(LocalDateTime.now());
        
        Todo savedTodo = todoService.saveTodoForUser(todo, currentUser.getId());
        TodoResponseDto responseDto = modelMapper.map(savedTodo, TodoResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{todoId}")
    @PreAuthorize("hasRole('ADMIN') or @todoService.isOwnerByEmail(#todoId, authentication.name)")
    public ResponseEntity<?> updateTodo(
            @PathVariable Long todoId,
            @Valid @RequestBody TodoRequestDto todoRequestDto,
            Authentication authentication) {
        Todo updatedTodo = todoService.updateTodo(todoId, todoRequestDto);
        if (updatedTodo == null) {
            return ResponseEntity.notFound().build();
        }
        TodoResponseDto responseDto = modelMapper.map(updatedTodo, TodoResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{todoId}/toggle-complete")
    @PreAuthorize("hasRole('ADMIN') or @todoService.isOwnerByEmail(#todoId, authentication.name)")
    public ResponseEntity<?> toggleTodoCompletion(@PathVariable Long todoId, Authentication authentication) {
        Todo updatedTodo = todoService.toggleTodoCompletion(todoId);
        if (updatedTodo == null) {
            return ResponseEntity.notFound().build();
        }
        TodoResponseDto responseDto = modelMapper.map(updatedTodo, TodoResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{todoId}")
    @PreAuthorize("hasRole('ADMIN') or @todoService.isOwnerByEmail(#todoId, authentication.name)")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoId, Authentication authentication) {
        boolean deleted = todoService.deleteTodo(todoId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Admin-only endpoint to get todos by specific user ID
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TodoResponseDto>> getTodosByUserId(@PathVariable Long userId) {
        List<Todo> todos = todoService.getTodosByUserId(userId);
        List<TodoResponseDto> dtoList = todos.stream()
                .map(todo -> modelMapper.map(todo, TodoResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
}