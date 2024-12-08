package com.SpringBootProject.proj1.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootProject.proj1.Entitys.User;
import com.SpringBootProject.proj1.Repositry.UserRepository;
import com.SpringBootProject.proj1.dto.AuthenticationRequest;
import com.SpringBootProject.proj1.dto.SignupRequest;
import com.SpringBootProject.proj1.dto.UserDto;
import com.SpringBootProject.proj1.service.AuthService;
import com.SpringBootProject.proj1.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public  static final String HEADER_STRING = "authorization";
    public  static final String TOKEN_PREFIX = "Bearer";
    private final AuthService authService;
    @PostMapping("/authenticate")
public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                       HttpServletResponse response) throws IOException, JSONException {
    try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));
    } catch (BadCredentialsException e) {
        throw new BadCredentialsException("Incorrect username or password.");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    java.util.Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails.getUsername());

    if (optionalUser.isPresent()) {
        response.getWriter().write(new JSONObject()
                .put("userId", optionalUser.get().getId())
                .put("role", optionalUser.get().getRole())
                .toString());
    }
    response.addHeader("Access-Control-Expose-Headers", "Authorization");
    response.addHeader("Access-Control-Allow-Headers","Authorization, X-PINGOTHER, Origin,X-Requested-With, Content-Type, Accept, X-Custom-header");
    //response.addHeader (HEADER_STRING,TOKEN_PREFIX + jwt);
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
}
@PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
