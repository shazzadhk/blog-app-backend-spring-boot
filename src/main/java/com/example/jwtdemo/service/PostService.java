package com.example.jwtdemo.service;

import com.example.jwtdemo.dao.CategoryDao;
import com.example.jwtdemo.dao.PostsDao;
import com.example.jwtdemo.dao.UserDao;
import com.example.jwtdemo.entity.Category;
import com.example.jwtdemo.entity.Posts;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.exceptions.ResourceNotFoundExceptions;
import com.example.jwtdemo.payloads.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostsDao postsDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UtilityService utilityService;

    public PostDto saveNewPost(PostDto postDto,Integer categoryId,Integer userId){
        Posts posts = new Posts();

        posts.setPostTitle(postDto.getTitle());
        posts.setPostContent(postDto.getPostBody());
        posts.setImageName("default.png");

        Date getFormatDate = utilityService.formatDate(new Date());

        posts.setAddedDate(getFormatDate);

        Category category = categoryDao.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundExceptions("category","categoryId",categoryId));

        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","id",userId));

        posts.setCategory(category);
        posts.setUser(user);

        postsDao.save(posts);

        //To Return PostDto We populate postDto with Post Data From DB
        PostDto postDto1 = new PostDto();
        postDto1.setTitle(posts.getPostTitle());
        postDto1.setPostBody(posts.getPostContent());
        postDto1.setPostImageName(posts.getImageName());

        Date getFormatDate1 = utilityService.formatDate(posts.getAddedDate());

        postDto1.setPostAddedDate(getFormatDate1);

        return postDto1;
    }

    public PostDto getPost(Integer postId){
        Posts post = postsDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("post","id",postId));

        PostDto postDto = new PostDto();
        postDto.setTitle(post.getPostTitle());
        postDto.setPostBody(post.getPostContent());
        postDto.setPostImageName(post.getImageName());

        Date getFormatDate = utilityService.formatDate(post.getAddedDate());

        postDto.setPostAddedDate(getFormatDate);
        return postDto;
    }

    public PostDto updateAPost(Integer postId,PostDto postDto){
        Posts post = postsDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("post","id",postId));

        post.setPostTitle(postDto.getTitle());
        post.setPostContent(postDto.getPostBody());

        postsDao.save(post);

        PostDto postDto1 = new PostDto();
        postDto1.setTitle(post.getPostTitle());
        postDto1.setPostBody(post.getPostContent());
        postDto1.setPostImageName(post.getImageName());

        Date getFormatDate = utilityService.formatDate(post.getAddedDate());

        postDto1.setPostAddedDate(getFormatDate);
        return postDto1;
    }

    public List<PostDto> getPostsByUserId(Integer userId){
        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","id",userId));
        List<Posts> postsList = postsDao.findAllByUser(user);

        List<PostDto> postDtoList = new ArrayList<>();

        postsList.forEach((post -> {
            PostDto postDto = new PostDto();

            postDto.setTitle(post.getPostTitle());
            postDto.setPostBody(post.getPostContent());
            postDto.setPostImageName(post.getImageName());

            Date getFormatDate = utilityService.formatDate(post.getAddedDate());
            postDto.setPostAddedDate(getFormatDate);

            postDtoList.add(postDto);
        }));

        return postDtoList;
    }

    public List<PostDto> getPostsByCategoryId(Integer categoryId){
        Category category = categoryDao.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("category","id",categoryId));
        List<Posts> postsList = postsDao.findAllByCategory(category);

        List<PostDto> postDtoList = new ArrayList<>();

        postsList.forEach((post -> {
            PostDto postDto = new PostDto();

            postDto.setTitle(post.getPostTitle());
            postDto.setPostBody(post.getPostContent());
            postDto.setPostImageName(post.getImageName());

            Date getFormatDate = utilityService.formatDate(post.getAddedDate());
            postDto.setPostAddedDate(getFormatDate);

            postDtoList.add(postDto);
        }));

        return postDtoList;
    }

}
