package com.sz.contrller;

import com.sz.entity.AuthRequest;
import com.sz.entity.User;
import com.sz.repository.UserRepository;
import com.sz.security.JwtUtil;
import com.sz.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RateLimiterService rateLimiterService;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder
    , RateLimiterService rateLimiterService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();
        if (!rateLimiterService.isAllowed(ip)) {
            return "Too many login attempts. Try again later.";
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return jwtUtil.generateToken(userRepository.findByUsername(request.getUsername()).get());
    }
}
