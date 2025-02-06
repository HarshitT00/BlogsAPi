package com.harshit.blogs.enums;

import lombok.Getter;

@Getter
public enum PostSortField {
    CREATED_AT("createdAt");

    private final String fieldName;

    PostSortField(String fieldName) {
        this.fieldName = fieldName;
    }
}
