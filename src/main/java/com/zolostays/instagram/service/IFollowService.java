package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.FollowMapDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import java.util.List;
import java.util.Optional;

public interface IFollowService {


    Optional<FollowMapDTO> followUser(Long user_id, Long follow_id) throws BaseException;

    boolean unfollowUser(Long user_id, Long follow_id) throws BaseException;

    List<FollowMapDTO> getFollower(Long user_id) throws UserDoesNotExistException;

    List<FollowMapDTO> getFollowing(Long user_id) throws UserDoesNotExistException;
}
