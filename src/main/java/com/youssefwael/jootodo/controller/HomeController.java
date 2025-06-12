package com.youssefwael.jootodo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController extends BaseController {
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> home(HttpServletRequest req) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to " + appName);
        response.put("status", "success");
        response.put("code", 200);
        return ResponseEntity.ok(response);
    }

}
