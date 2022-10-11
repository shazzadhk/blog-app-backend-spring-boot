package com.example.jwtdemo.controller;


import com.example.jwtdemo.dao.UserDao;
import com.example.jwtdemo.exceptions.ApiException;
import com.example.jwtdemo.exceptions.ResourceNotFoundExceptions;
import com.example.jwtdemo.payloads.JwtAuthRequest;
import com.example.jwtdemo.payloads.JwtAuthResponse;
import com.example.jwtdemo.payloads.UserDto;
import com.example.jwtdemo.security.CustomUserDetails;
import com.example.jwtdemo.security.JwtTokenHelper;
import com.example.jwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetails customUserDetails;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        this.authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.customUserDetails.loadUserByUsername(request.getEmail());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        com.example.jwtdemo.entity.User user = userDao.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","email"+request.getEmail(),0));

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        response.setUserDto(userDto);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {

            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            throw new ApiException("Invalid username or password !!");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){

        System.out.println(userDto);

        UserDto savedUserDto = userService.saveNewUser(userDto);
        return new ResponseEntity<UserDto>(savedUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
