package com.example.jwtdemo.service;

import com.example.jwtdemo.dao.RoleDao;
import com.example.jwtdemo.dao.UserDao;
import com.example.jwtdemo.entity.Role;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.exceptions.ResourceNotFoundExceptions;
import com.example.jwtdemo.payloads.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto saveNewUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleDao.findByRoleName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setRoles(roles);
        userDao.save(user);
        return userDto;
    }

    public UserDto getSpecificUser(Integer user_id){
        User user = userDao.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","id",user_id));
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    public void deleteSpecificUser(Integer user_id){
        User user = userDao.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","id",user_id));
        userDao.deleteById(user_id);
    }

    public List<UserDto> findAllUsers(){
        List<User> userList = userDao.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        userList.forEach((user -> {
            UserDto userDto = new UserDto();
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDtoList.add(userDto);
        }));

        return userDtoList;
    }
}
