package com.harshit.blogs.controller;


import com.harshit.blogs.dto.*;
import com.harshit.blogs.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<PostResponse> addPost(@Valid @RequestBody AddPostRequest addPostRequest){
        PostResponse newPost = postService.addPost(addPostRequest);
        return ResponseEntity.ok(newPost);
    }

    @GetMapping("")
    public ResponseEntity<CustomPageResponse<PostResponse>> getAllPosts(@ModelAttribute GetPostQuery query){
        CustomPageResponse<PostResponse> posts = postService.getAllPost(query);
        return ResponseEntity.ok(posts);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest updatePostRequest){
        PostResponse updatedPost = postService.updatePost(id,updatePostRequest);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        PostResponse post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/userName/{userName}")
    public ResponseEntity<CustomPageResponse<PostResponse>> getPostByUserName(@PathVariable String userName , @ModelAttribute GetPostQuery query){
        CustomPageResponse<PostResponse> posts = postService.getPostByUserName(query, userName);
        return ResponseEntity.ok(posts);
    }

}
