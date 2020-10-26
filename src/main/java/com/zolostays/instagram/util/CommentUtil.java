package com.zolostays.instagram.util;


import com.zolostays.instagram.exception.CommentDoesNotExistException;
import com.zolostays.instagram.model.Comment;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommentUtil {

    @Autowired
    private CommentRepository commentRepository;

    public Comment getComment(Long comment_id, User user, Post post) throws CommentDoesNotExistException {
        Optional<Comment> commentOptional = commentRepository.findByIdAndUserAndPostAndCommentIdIsNull(comment_id, user, post);
        if(commentOptional.isEmpty()){
            throw new CommentDoesNotExistException(comment_id.toString());
        }
        return commentOptional.get();
    }
}

