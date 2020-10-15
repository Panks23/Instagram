package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.service.IFollowService;
import org.springframework.web.bind.annotation.*;

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
        return followService.followUser(user_id, following_id);
    }

    @DeleteMapping("/{user_id}/follow/{following_id}")
    public ResponseDTO unFollowUser(@PathVariable("user_id") Long user_id,
                                    @PathVariable("following_id") Long following_id){
        return followService.unfollowUser(user_id, following_id);
    }

    @GetMapping("/{user_id}/following")
    public ResponseDTO getFollowing(@PathVariable("user_id") Long user_id){
        return followService.getFollowing(user_id);
    }

    @GetMapping("/{user_id}/follower")
    public ResponseDTO getFollower(@PathVariable("user_id") Long user_id){
        return followService.getFollower(user_id);
    }
}
