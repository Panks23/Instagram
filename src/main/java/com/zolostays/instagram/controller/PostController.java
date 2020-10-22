package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.service.IPostService;
import com.zolostays.instagram.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instagram/v1/user")
public class PostController {

    @Autowired
    private IPostService postService;

    @PostMapping("/{user_id}/post")
    public ResponseDTO createPost(@RequestBody PostDTO postDTO, @PathVariable("user_id") Long user_id){
        try{
            PostDTO resultPostDTO = postService.createPost(postDTO, user_id);
            return Mapper.responseDTOSingle(resultPostDTO, "Post Created");
        }catch (UserDoesNotExistException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }

    @DeleteMapping("/{user_id}/post/{post_id}")
    public ResponseDTO deletePost(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        try{
            postService.deletePost(user_id, post_id);
            return Mapper.objectDeleted("Post deleted");
        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }

    }

    @GetMapping("/{user_id}/post")
    public ResponseDTO getAllPost(@PathVariable("user_id") Long user_id){
        try{
            List<PostDTO> postDTOList = postService.getAllPost(user_id);
                return Mapper.responseDTO(postDTOList, "You have recieved post for the given user id");
        }catch (UserDoesNotExistException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }

    }

    @GetMapping("/{user_id}/post/{post_id}")
    public ResponseDTO getPostById(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        try {
            Optional<PostDTO> postDTOOptional = postService.getPost(user_id, post_id);
            return Mapper.responseDTOSingle(postDTOOptional.get(), "You have got the response for the given post id");
        }catch (BaseException e){
            return Mapper.objectDoesNotExist(e.getMessage());
        }
    }

    @PutMapping("/{user_id}/post/{post_id}")
    public ResponseDTO updatePost(@RequestBody PostDTO postDTO, @PathVariable("user_id") Long user_id,
                                  @PathVariable("post_id") Long post_id){
        try{
             return Mapper.responseDTOSingle(postService.updatePost(user_id, post_id, postDTO), "User Updated");
        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }
}
