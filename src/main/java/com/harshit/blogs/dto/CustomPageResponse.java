package com.harshit.blogs.dto;

import java.util.List;

public class CustomPageResponse<T> {
    private List<T> data;
    private int pageSize;
    private int pageNumber;
    private long totalItems;
    private boolean hasMoreItems;

    public CustomPageResponse(List<T> data, int pageSize, int pageNumber, long totalItems, boolean hasMoreItems) {
        this.data = data;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalItems = totalItems;
        this.hasMoreItems = hasMoreItems;
    }

    // Static factory method to create from Page object
    public static <T> CustomPageResponse<T> fromPage(org.springframework.data.domain.Page<T> page) {
        return new CustomPageResponse<>(
                page.getContent(),
                page.getSize(),
                page.getNumber(),
                page.getTotalElements(),
                !page.isLast()
        );
    }

    // Getters and setters
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
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

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public boolean isHasMoreItems() {
        return hasMoreItems;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
    }
}