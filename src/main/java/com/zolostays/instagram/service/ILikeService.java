package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ResponseDTO;

public interface ILikeService {

    ResponseDTO likePost(Long user_id, Long post_id);

    ResponseDTO getAllLike(Long post_id);

    ResponseDTO dislikePost(Long user_id, Long post_id);
}
