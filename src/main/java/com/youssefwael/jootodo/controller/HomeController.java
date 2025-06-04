package com.youssefwael.jootodo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        System.out.println("Home page of " + appName);
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Welcome to " + appName);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    private String getViewName(){
        return "index.html";
    }
}
