package com.example.jwtdemo.controller;

import com.example.jwtdemo.payloads.ApiResponse;
import com.example.jwtdemo.payloads.PostDto;
import com.example.jwtdemo.payloads.PostResponse;
import com.example.jwtdemo.service.FileService;
import com.example.jwtdemo.service.PostService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //add a new post with category id and user id
    @PostMapping("/add-post/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> addNewPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer categoryId,
                                              @PathVariable Integer userId){

        return new ResponseEntity<PostDto>(postService.saveNewPost(postDto,categoryId,userId), HttpStatus.CREATED);
    }

    //get all posts
    @GetMapping("/all-posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize,
                                                    @RequestParam(value = "pageNumber",defaultValue = "1",required = false) Integer pageNumber,
                                                    @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
                                                    @RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection){

        PostResponse postResponse = this.postService.getAllPostsList(pageSize,pageNumber,sortBy,sortDirection);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
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

    //delete a post
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        postService.deleteAPost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted succefully",true),HttpStatus.OK);
    }

    //search post by keyword
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> getPostByKeyWord(@RequestParam(value = "keyword",required = true) String keyword){
        return new ResponseEntity<List<PostDto>>(this.postService.searchPost(keyword),HttpStatus.OK);
    }


    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                   @PathVariable Integer postId) throws IOException {

        PostDto postDto = this.postService.getPost(postId);

        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updateAPost(postId,postDto);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

    }

    @GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
