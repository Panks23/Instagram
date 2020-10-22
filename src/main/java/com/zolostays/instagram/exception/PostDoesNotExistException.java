package com.zolostays.instagram.exception;


public class PostDoesNotExistException extends BaseException{
    public PostDoesNotExistException(String post_id) {
        super("Post doesn't exist with given id: "+ post_id);
    }
}
