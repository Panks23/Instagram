package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.service.IPostService;
import com.zolostays.instagram.util.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseDTO deletePost(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        ResponseDTO responseDTO = postService.deletePost(user_id, post_id);
        return responseDTO;
    }

    @GetMapping("/{user_id}/post")
    public ResponseDTO getAllPost(@PathVariable("user_id") Long user_id){
        List<PostDTO> postDTOList = postService.getAllPost(user_id);
        if(!postDTOList.isEmpty()){
            return Mapper.responseDTO(postDTOList, "You have recieved post for the given user id");
        }
        return  Mapper.responseDTOSingle(null, "User doesn't exist");
    }

    @GetMapping("/{user_id}/post/{post_id}")
    public ResponseDTO getPostById(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        Optional<PostDTO> postDTOOptional = postService.getPost(user_id, post_id);
        if(postDTOOptional.isPresent()){
            return Mapper.responseDTOSingle(postDTOOptional.get(), "You have got the response for the given post id");
        }
        return Mapper.objectDoesNotExist("Post doesn't exist for the given ID");
    }

    @PutMapping("/{user_id}/post/{post_id}")
    public ResponseDTO updatePost(@RequestBody PostDTO postDTO, @PathVariable("user_id") Long user_id,
                                  @PathVariable("post_id") Long post_id){
        return postService.updatePost(postDTO, user_id, post_id);
    }
}
