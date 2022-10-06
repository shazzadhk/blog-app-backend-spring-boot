package com.example.jwtdemo.payloads;

import java.util.List;

public class PostResponse {

    private List<PostDto> postContent;
    private int pageSize;
    private int pageNumber;
    private long totalElements;
    private int totalPages;
    private boolean isLastPage;

    public PostResponse() {
    }

    public List<PostDto> getPostContent() {
        return postContent;
    }

    public void setPostContent(List<PostDto> postContent) {
        this.postContent = postContent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }
}
