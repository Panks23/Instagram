package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.LikeDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.service.ILikeService;
import com.zolostays.instagram.util.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/instagram/api/v1/user")
public class LikeController {

    public ILikeService likeService;

    public LikeController(ILikeService likeService){
        this.likeService = likeService;
    }

    @PostMapping("/{user_id}/post/{post_id}/like")
    public ResponseDTO likePost(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        try {
            Optional<LikeDTO> optionalLikeDTO =  likeService.likePost(user_id, post_id);
            if(optionalLikeDTO.isEmpty()){
                return Mapper.responseDTO(new ArrayList<>(), "You have already liked");
            }
            return Mapper.responseDTOSingle(optionalLikeDTO.get() , "You Liked the post");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }

    @GetMapping("/{user_id}/post/{post_id}/like")
    public ResponseDTO getAllLike(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        try{
            return Mapper.responseDTO(likeService.getAllLike(user_id, post_id), "You have got all your likes");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }

    @DeleteMapping("/{user_id}/post/{post_id}/like")
    public ResponseDTO dislikePost(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        try{
            if(likeService.dislikePost(user_id, post_id)){
                return Mapper.responseDTO(new ArrayList<>(), "You have disliked the post");
            }
            return Mapper.responseDTO(new ArrayList<>(), "Failed to dislike as you never liked this post");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }

}
