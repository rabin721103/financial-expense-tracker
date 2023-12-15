package com.practice.financialtracker.controller;

import com.practice.financialtracker.exceptions.CustomException;
import com.practice.financialtracker.model.AuthRequest;
import com.practice.financialtracker.model.TokenResponse;
import com.practice.financialtracker.model.User;
import com.practice.financialtracker.service.CustomUserDetails;
import com.practice.financialtracker.service.JwtService;
import com.practice.financialtracker.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/user/login")
    public ResponseEntity<TokenResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {

            // Attempt to authenticate the user using the provided credentials
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

            // If the authentication is successful, generate a JWT token
            if (authentication.isAuthenticated()) {
                //The authenticated user is represented by a Principal object, which typically includes details like the username.
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                User user = userService.getUserByUserName(customUserDetails.getUsername());
                String token = jwtService.generateToken(user);
                final Cookie cookie = new Cookie("auth", token);
                cookie.setSecure(false);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(50400);
                cookie.setPath("/api");
                response.addCookie(cookie);

                return ResponseEntity.ok().body(new TokenResponse(token));
            } else {
                throw new CustomException("Invalid credentials");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
