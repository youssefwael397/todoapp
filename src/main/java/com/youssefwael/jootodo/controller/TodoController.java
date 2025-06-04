package com.youssefwael.jootodo.controller;

import com.youssefwael.jootodo.dto.TodoResponseDto;
import com.youssefwael.jootodo.dto.UserResponseDto;
import com.youssefwael.jootodo.entity.Todo;
import com.youssefwael.jootodo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos(){
        List<TodoResponseDto> todos = todoService.getAllTodos().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(todos);
    }

    private TodoResponseDto convertToResponseDto(Todo todo){
        TodoResponseDto dto = new TodoResponseDto();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setCompleted(todo.getCompleted());
        dto.setCreatedAt(todo.getCreatedAt());
        dto.setDueDate(todo.getDueDate());

        // Convert user to UserResponseDto
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(todo.getUser().getId());
        userDto.setUsername(todo.getUser().getUsername());
        userDto.setEmail(todo.getUser().getEmail());
        userDto.setRole(todo.getUser().getRole());
        dto.setUser(userDto);

        return dto;
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId){
        Todo existingTodo = todoService.getTodoById(todoId);
        if( existingTodo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Todo not found"));
        }
        return ResponseEntity.ok(convertToResponseDto(todoService.getTodoById(todoId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoResponseDto>> getTodosByUserId(@PathVariable Long userId) {
        List<TodoResponseDto> todos = todoService.getTodosByUserId(userId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(todos);
    }
}
