package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import java.util.Optional;

public interface IPostService {

    ResponseDTO<Optional<PostDTO>> getPost(Long id);
    ResponseDTO getAllPost();
    ResponseDTO deletePost(Long id);
    ResponseDTO createPost(PostDTO postDTO);
    ResponseDTO updatePost(PostDTO postDTO);
}
