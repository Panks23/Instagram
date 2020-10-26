package com.zolostays.instagram.exception;

public class ReplyDoesNotExistException extends BaseException{

    public ReplyDoesNotExistException(String reply_id){
        super("Reply does not exist with reply id "+ reply_id);
    }
}
