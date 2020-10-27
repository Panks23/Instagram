package com.zolostays.instagram.sampleData.commentData;

import com.zolostays.instagram.dto.CommentDTO;

public class CommentData {

    public static CommentDTO getCommentData(){
        return new CommentDTO().setComment("Hi Comment").setCommentId(null);
    }

    public static CommentDTO getUpdatedCommentData(){
        return new CommentDTO().setComment("Hi I am updated").setCommentId(null);
    }
}
