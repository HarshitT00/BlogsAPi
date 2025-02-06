package com.harshit.blogs.dto;

import com.harshit.blogs.enums.PostSortField;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class GetPostQuery {
    private int pageNumber = 0;
    private int pageSize = 10;
    private PostSortField sortBy = PostSortField.CREATED_AT;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
}