package com.zolostays.instagram.dto;

import com.zolostays.instagram.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentDTO {

    private String comment;


    private PostDTO postDTO;


    private UserDTO userDTO;


    private Comment commentId;

    private Timestamp timeStamp;
}
