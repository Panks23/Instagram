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
    @PostMapping("/{id}/post")
    public ResponseDTO createPost(@RequestBody PostDTO postDTO, @PathVariable("id") Long id){
        ResponseDTO responseDTO = postService.createPost(postDTO, id);
        return responseDTO;
    }

    @DeleteMapping("/{user_id}/post/{id}")
    public ResponseDTO deletePost(@PathVariable("id") Long id){
        ResponseDTO responseDTO = postService.deletePost(id);
        return responseDTO;
    }

    @GetMapping("/{id}/post")
    public ResponseDTO getAllPost(@PathVariable("id") Long user_id){
        ResponseDTO responseDTO = postService.getAllPost(user_id);
        return  responseDTO;
    }

    @GetMapping("/{user_id}/post/{id}")
    public ResponseDTO getPostById(@PathVariable("id") Long id){
        return postService.getPost(id);
    }

    @PutMapping("/{user_id}/post")
    public ResponseDTO updatePost(@RequestBody PostDTO postDTO, @PathVariable("user_id") Long id){
        return postService.updatePost(postDTO, id);
    }
}
