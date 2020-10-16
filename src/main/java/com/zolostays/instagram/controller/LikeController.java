package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.service.ILikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instagram/api/v1/user")
public class LikeController {

    public ILikeService likeService;

    public LikeController(ILikeService likeService){
        this.likeService = likeService;
    }

    @PostMapping("/{user_id}/post/{post_id}/like")
    public ResponseDTO likePost(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        return likeService.likePost(user_id, post_id);
    }

    @GetMapping("/{user_id}/post/{post_id}/like")
    public ResponseDTO getAllLike(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        return likeService.getAllLike(user_id, post_id);
    }

    @DeleteMapping("/{user_id}/post/{post_id}/like")
    public ResponseDTO dislikePost(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        return likeService.dislikePost(user_id, post_id);
    }

}
