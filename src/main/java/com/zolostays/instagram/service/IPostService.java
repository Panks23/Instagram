package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IPostService {

    Optional<PostDTO> getPost(Long user_id, Long post_id);

    List<PostDTO> getAllPost(Long user_id);

    ResponseDTO deletePost(Long user_id, Long post_id);

    ResponseDTO createPost(PostDTO postDTO, Long user_id);

    ResponseDTO updatePost(PostDTO postDTO, Long user_id, Long post_id);
}
