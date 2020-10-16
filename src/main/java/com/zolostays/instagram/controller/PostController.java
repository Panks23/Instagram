package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.service.IPostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instagram/v1/user")
public class PostController {

    private IPostService postService;

    public PostController(IPostService postService){
        this.postService = postService;
    }
    @PostMapping("/{user_id}/post")
    public ResponseDTO createPost(@RequestBody PostDTO postDTO, @PathVariable("user_id") Long user_id){
        ResponseDTO responseDTO = postService.createPost(postDTO, user_id);
        return responseDTO;
    }

    @DeleteMapping("/{user_id}/post/{post_id}")
    public ResponseDTO deletePost(@PathVariable("post_id") Long post_id){
        ResponseDTO responseDTO = postService.deletePost(post_id);
        return responseDTO;
    }

    @GetMapping("/{user_id}/post")
    public ResponseDTO getAllPost(@PathVariable("user_id") Long user_id){
        ResponseDTO responseDTO = postService.getAllPost(user_id);
        return  responseDTO;
    }

    @GetMapping("/{user_id}/post/{post_id}")
    public ResponseDTO getPostById(@PathVariable("post_id") Long post_id){
        return postService.getPost(post_id);
    }

    @PutMapping("/{user_id}/post/{post_id}")
    public ResponseDTO updatePost(@RequestBody PostDTO postDTO, @PathVariable("user_id") Long user_id,
                                  @PathVariable("post_id") Long post_id){
        return postService.updatePost(postDTO, user_id, post_id);
    }
}
