package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.exception.BaseException;

import java.util.List;

public interface ICommentService {

    CommentDTO createComment(CommentDTO commentDTO, Long user_id, Long post_id) throws BaseException;

    List<CommentDTO> getAllComment(Long user_id, Long post_id) throws BaseException;

    CommentDTO updateComment(CommentDTO commentDTO, Long user_id, Long post_id, Long comment_id) throws BaseException;

    void deleteComment(Long user_id,  Long post_id, Long comment_id) throws BaseException;


    CommentDTO reply(CommentDTO commentDTO, Long comment_id, Long user_id, Long post_id) throws BaseException;


    CommentDTO updateReply(CommentDTO commentDTO, Long comment_id, Long user_id, Long reply_id, Long post_id) throws BaseException;

    void deleteReply(Long user_id, Long post_id, Long comment_id, Long reply_id) throws BaseException;

    List<CommentDTO> getCommentAndReplies(Long user_id, Long post_id, Long comment_id) throws BaseException;


}
