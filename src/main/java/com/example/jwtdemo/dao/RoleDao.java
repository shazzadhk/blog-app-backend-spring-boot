package com.example.jwtdemo.dao;

import com.example.jwtdemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role,Integer> {

    Role findByRoleName(String roleName);
}
