package com.example.jwtdemo.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class PostDto {

    @NotBlank
    @Size(min = 15,max = 500)
    private String title;

    private String postBody;

    private String postImageName;

    private Date postAddedDate;

    public PostDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPostImageName() {
        return postImageName;
    }

    public void setPostImageName(String postImageName) {
        this.postImageName = postImageName;
    }

    public Date getPostAddedDate() {
        return postAddedDate;
    }

    public void setPostAddedDate(Date postAddedDate) {
        this.postAddedDate = postAddedDate;
    }

}
