package com.zolostays.instagram.exception;

public class UserDoesNotExistException extends BaseException {
    public UserDoesNotExistException(String id) {
        super("User doesn't exist of given id: " + id);
    }
}
