package com.example.jwtdemo.security;

import com.example.jwtdemo.dao.UserDao;
import com.example.jwtdemo.exceptions.ResourceNotFoundExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userDao.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","email: "+username,0));
    }
}
