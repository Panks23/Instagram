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
}
