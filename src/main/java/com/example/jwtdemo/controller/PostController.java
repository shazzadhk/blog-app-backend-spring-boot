package com.example.jwtdemo.controller;

import com.example.jwtdemo.entity.Posts;
import com.example.jwtdemo.payloads.PostDto;
import com.example.jwtdemo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/add-post/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> addNewPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer categoryId,
                                              @PathVariable Integer userId){

        return new ResponseEntity<PostDto>(postService.saveNewPost(postDto,categoryId,userId), HttpStatus.CREATED);
    }

    //get a post by id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId){

        return new ResponseEntity<PostDto>(postService.getPost(postId),HttpStatus.OK);
    }

    //update a post

    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){

        return new ResponseEntity<PostDto>(postService.updateAPost(postId,postDto),HttpStatus.OK);
    }

    //get posts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId){

        return new ResponseEntity<List<PostDto>>(postService.getPostsByUserId(userId),HttpStatus.OK);
    }

    //get posts by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer categoryId){

        return new ResponseEntity<List<PostDto>>(postService.getPostsByCategoryId(categoryId),HttpStatus.OK);
    }
}
