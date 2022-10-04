package com.example.jwtdemo.dao;

import com.example.jwtdemo.entity.Category;
import com.example.jwtdemo.entity.Posts;
import com.example.jwtdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsDao extends JpaRepository<Posts,Integer>{

    List<Posts> findAllByUser(User user);

    List<Posts> findAllByCategory(Category category);
}
