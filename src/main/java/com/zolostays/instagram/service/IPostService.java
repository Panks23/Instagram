package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import java.util.List;
import java.util.Optional;

public interface IPostService {

    Optional<PostDTO> getPost(Long user_id, Long post_id) throws BaseException;

    List<PostDTO> getAllPost(Long user_id) throws UserDoesNotExistException;

    void deletePost(Long user_id, Long post_id) throws BaseException;

    PostDTO createPost(PostDTO postDTO, Long user_id) throws UserDoesNotExistException;

    PostDTO updatePost(Long user_id, Long post_id, PostDTO postDTO) throws BaseException;
}
