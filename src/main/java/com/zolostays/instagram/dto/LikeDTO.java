package com.zolostays.instagram.dto;

import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {

    private Long id;

    private PostDTO postDTO;

    private UserDTO userDTO;

    private Timestamp timeStamp;
}
