package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.LikeDTO;
import com.zolostays.instagram.exception.BaseException;
import java.util.List;
import java.util.Optional;

public interface ILikeService {

    Optional<LikeDTO> likePost(Long user_id, Long post_id) throws BaseException;

    List<LikeDTO> getAllLike(Long user_id, Long post_id) throws BaseException;

    boolean dislikePost(Long user_id, Long post_id) throws BaseException;
}
