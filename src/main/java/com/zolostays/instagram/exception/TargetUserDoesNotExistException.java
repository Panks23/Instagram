package com.zolostays.instagram.exception;

public class TargetUserDoesNotExistException extends BaseException {
    public TargetUserDoesNotExistException(String target_id) {
        super("Target User Does Not Exist with target Id: "+ target_id);
    }
}
