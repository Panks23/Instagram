package com.zolostays.instagram.exception;

public class CommentDoesNotExistException extends BaseException{

    public CommentDoesNotExistException(String comment_id) {
        super("Comment does not exist with comment id: "+ comment_id);
    }
}
