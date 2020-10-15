package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ResponseDTO;

public interface IFollowService {

    ResponseDTO followUser(Long user_id, Long follow_id);

    ResponseDTO unfollowUser(Long user_id, Long follow_id);

    ResponseDTO getFollower(Long user_id);

    ResponseDTO getFollowing(Long user_id);
}
