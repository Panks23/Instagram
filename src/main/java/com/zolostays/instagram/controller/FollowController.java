package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.FollowMapDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.service.IFollowService;
import com.zolostays.instagram.util.Mapper;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/instagram/api/v1/user")
public class FollowController {

    private IFollowService followService;


    public FollowController(IFollowService followService){
        this.followService = followService;
    }

    @PostMapping("/{user_id}/follow/{following_id}")
    public ResponseDTO followUser(@PathVariable("user_id") Long user_id,
                                  @PathVariable("following_id") Long following_id){
        if(user_id==following_id){
            return Mapper.responseDTO(new ArrayList<>(), "You can not follow yourself");
        }
        try{
            Optional<FollowMapDTO> followMapDTOOptional = followService.followUser(user_id, following_id);
            if(followMapDTOOptional.isPresent()){
                return Mapper.responseDTOSingle(followMapDTOOptional, "You have followed");
            }
            return Mapper.responseDTO(new ArrayList<>(), "You already Follow");
        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }

    @DeleteMapping("/{user_id}/follow/{following_id}")
    public ResponseDTO unFollowUser(@PathVariable("user_id") Long user_id,
                                    @PathVariable("following_id") Long following_id){
        try{
            followService.unfollowUser(user_id, following_id);
            return Mapper.responseDTO(new ArrayList<>(), "You have unfollowed");
        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }

    @GetMapping("/{user_id}/following")
    public ResponseDTO getFollowing(@PathVariable("user_id") Long user_id){
        try{
            return Mapper.responseDTO(followService.getFollowing(user_id), "Your Following");
        }catch (UserDoesNotExistException userDoesNotExistException){
            return Mapper.responseDTOSingle(null, userDoesNotExistException.getMessage());
        }
    }

    @GetMapping("/{user_id}/follower")
    public ResponseDTO getFollower(@PathVariable("user_id") Long user_id){
        try{
             return Mapper.responseDTO(followService.getFollower(user_id), "You have recieved all your followers");
        }catch (UserDoesNotExistException userDoesNotExistException){
            return Mapper.responseDTOSingle(null, userDoesNotExistException.getMessage());
        }

    }
}
