package com.example.jwtdemo.payloads;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {

    @NotEmpty(message = "Name can not be empty")
    @Size(min = 4,message = "Name must be minimum of 4 characters")
    private String name;

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email can not be empty")
    private String email;

    @Size(min = 4,max = 10,message = "password must be between 4 to 10 characters")

    @NotEmpty(message = "Password can not be empty")

    @NotEmpty(message = "Password can not be mt")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
