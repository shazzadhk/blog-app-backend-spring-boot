package com.example.jwtdemo.service;

import com.example.jwtdemo.dao.CategoryDao;
import com.example.jwtdemo.dao.PostsDao;
import com.example.jwtdemo.dao.UserDao;
import com.example.jwtdemo.entity.Category;
import com.example.jwtdemo.entity.Posts;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.exceptions.ResourceNotFoundExceptions;
import com.example.jwtdemo.payloads.PostDto;
import com.example.jwtdemo.payloads.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper modelMapper;

    public PostDto saveNewPost(PostDto postDto,Integer categoryId,Integer userId){
        Posts posts = new Posts();

        posts.setPostTitle(postDto.getPostTitle());
        posts.setPostContent(postDto.getPostContent());
//        posts.setImageName("default.png");

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
        PostDto postDto1 = this.modelMapper.map(posts,PostDto.class);
        postDto1.setAddedDate(utilityService.formatDate(posts.getAddedDate()));

        return postDto1;
    }

    public PostDto getPost(Integer postId){
        Posts post = postsDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("post","id",postId));

        return this.modelMapper.map(post,PostDto.class);
    }

    public PostDto updateAPost(Integer postId,PostDto postDto){
        Posts post = postsDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("post","id",postId));

        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setImageName(postDto.getImageName());

        postsDao.save(post);

        return this.modelMapper.map(post,PostDto.class);

    }

    public List<PostDto> getPostsByUserId(Integer userId){
        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","id",userId));
        List<Posts> postsList = postsDao.findAllByUser(user);

        List<PostDto> postDtoList = new ArrayList<>();

        postsList.forEach((post -> {
            PostDto postDto = this.modelMapper.map(post,PostDto.class);

            postDto.setAddedDate(utilityService.formatDate(post.getAddedDate()));

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
            PostDto postDto = this.modelMapper.map(post,PostDto.class);

            postDto.setAddedDate(utilityService.formatDate(post.getAddedDate()));

            postDtoList.add(postDto);
        }));

        return postDtoList;
    }

    public void deleteAPost(Integer postId){

        Posts post = postsDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("post","id",postId));

        postsDao.deleteById(postId);
    }

    public PostResponse getAllPostsList(Integer pageSize, Integer pageNumber, String sortBy, String sortDir){

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Posts> pageOfPost = postsDao.findAll(pageable);

        List<Posts> postsList = pageOfPost.getContent();

        List<PostDto> postDtoList = postsList.stream().
                map((posts) -> this.modelMapper.map(posts,PostDto.class)).
                collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setPostContent(postDtoList);
        postResponse.setPageNumber(pageOfPost.getNumber());
        postResponse.setPageSize(pageOfPost.getSize());
        postResponse.setTotalElements(pageOfPost.getTotalElements());
        postResponse.setTotalPages(pageOfPost.getTotalPages());
        postResponse.setLastPage(pageOfPost.isLast());

        return postResponse;
    }

    public List<PostDto> searchPost(String searchKeyword){
        List<Posts> postsList = postsDao.searchPost("%"+searchKeyword+"%");

        List<PostDto> postDtoList = new ArrayList<>();

        postsList.forEach((post) -> {
            PostDto postDto = this.modelMapper.map(post,PostDto.class);
            postDtoList.add(postDto);
        });

        return postDtoList;
    }



}
