package com.example.jwtdemo.dao;

import com.example.jwtdemo.entity.Category;
import com.example.jwtdemo.entity.Posts;
import com.example.jwtdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsDao extends JpaRepository<Posts,Integer>{

    List<Posts> findAllByUser(User user);

    List<Posts> findAllByCategory(Category category);

    @Query("select p from Posts p where p.postTitle like :key")
    List<Posts> searchPost(@Param("key") String keyword);
}
