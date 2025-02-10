package com.harshit.blogs.service;


import com.harshit.blogs.dto.LoginRequest;
import com.harshit.blogs.dto.LoginResponse;
import com.harshit.blogs.dto.RegisterRequest;
import com.harshit.blogs.exceptionHandling.ApiException;
import com.harshit.blogs.exceptionHandling.ErrorCode;
import com.harshit.blogs.model.Users;
import com.harshit.blogs.repository.UserRepository;
import com.harshit.blogs.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.harshit.blogs.security.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public String register(RegisterRequest request) {
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new ApiException(ErrorCode.BUSINESS_ERROR, "Username already taken");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiException(ErrorCode.BUSINESS_ERROR, "Email already registered");
        }

        Users user = new Users();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "User registered successfully!";
    }

    public LoginResponse login(LoginRequest request) {
        Users user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new ApiException(
                        ErrorCode.AUTHENTICATION_FAILED,
                        "Invalid username or password"
                ));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException(
                    ErrorCode.AUTHENTICATION_FAILED,
                    "Invalid username or password"
            );
        }
        LoginResponse res =  new LoginResponse();
        res.setToken(jwtService.generateToken(user.getUserName()));
        return res;
    }
}