package com.example.caseGrab.controller;

import com.example.caseGrab.dto.req.SIgnInRequestDTO;
import com.example.caseGrab.dto.req.SignUpRequestDTO;
import com.example.caseGrab.dto.res.BodyResponseDTO;
import com.example.caseGrab.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequestDTO requestDTO) {
        return userService.signUp(requestDTO);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<BodyResponseDTO> signIn(@Valid @RequestBody SIgnInRequestDTO requestDTO) {
        return userService.signIn(requestDTO);
    }
}