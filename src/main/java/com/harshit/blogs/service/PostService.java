package com.harshit.blogs.service;

import com.harshit.blogs.dto.*;
import com.harshit.blogs.exceptionHandling.ApiException;
import com.harshit.blogs.exceptionHandling.ErrorCode;
import com.harshit.blogs.model.Post;
import com.harshit.blogs.model.Users;
import com.harshit.blogs.repository.PostRepository;
import com.harshit.blogs.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse addPost(@NotNull AddPostRequest addPostRequest){

        Optional<Users> existingUser = userRepository.findByUserName(addPostRequest.getUserName());

        if(existingUser.isEmpty()){
            throw new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "User not found");
        }

        Post newPost = new Post();
        newPost.setContent(addPostRequest.getContent());
        newPost.setUserName(addPostRequest.getUserName());
        newPost.setTitle(addPostRequest.getTitle());

        Post savedPost = postRepository.save(newPost);

        return postResponseMapper(savedPost);
    }

    public PostResponse updatePost(@NotNull Long id, @NotNull UpdatePostRequest updatePostRequest){

        Post post = postRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found"));

        if(!post.getUserName().equals(updatePostRequest.getUserName())){
            throw new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }

        post.setContent(updatePostRequest.getContent());
        post.setTitle(updatePostRequest.getTitle());

        return postResponseMapper(postRepository.save(post));
    }

    public PostResponse getPost(@NotNull Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            throw new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }
        return postResponseMapper(post.get());
    }

    public Page<PostResponse> getPostByUserName(@NotNull GetPostQuery query, @NotNull String userName){
        Sort sort = Sort.by(query.getSortDirection(), query.getSortBy().getFieldName());
        Pageable pageable = PageRequest.of(query.getPageNumber(),query.getPageSize(),sort);
        Page<Post> posts = postRepository.findByUserName(userName,pageable);
        return posts.map(PostService::postResponseMapper);
    }

    public CustomPageResponse<PostResponse> getAllPost(@NotNull GetPostQuery query) {
        Sort sort = Sort.by(query.getSortDirection(), query.getSortBy().getFieldName());
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostResponse> postResponses = postPage.getContent().stream()
                .map(PostService::postResponseMapper)
                .collect(Collectors.toList());
        return new CustomPageResponse<>(
                postResponses,
                postPage.getSize(),
                postPage.getNumber(),
                postPage.getTotalElements(),
                !postPage.isLast()
        );
    }

    public void deletePost(@NotNull Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found"));
        postRepository.delete(post);
    }


    public static PostResponse postResponseMapper(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setUserName(post.getUserName());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt());
        if(post.getUpdatedAt() != null) response.setUpdatedAt(post.getUpdatedAt());
        return response;
    }

}
