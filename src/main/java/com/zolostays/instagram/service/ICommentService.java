package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;

public interface ICommentService {

    ResponseDTO createComment(CommentDTO commentDTO, Long user_id, Long post_id);

    ResponseDTO getAllComment(Long user_id, Long post_id);

    ResponseDTO replyToComment(CommentDTO commentDTO, Long comment_id, Long user_id);

    ResponseDTO updateReplyToComment(CommentDTO commentDTO, Long comment_id, Long user_id, Long reply_id);

    ResponseDTO deleteReplyToComment(Long reply_id, Long comment_id, Long user_id);

    ResponseDTO getCommentAndReplies(Long comment_id);

    ResponseDTO updateComment(CommentDTO commentDTO, Long comment_id);

    ResponseDTO deleteComment(Long comment_id);


}
