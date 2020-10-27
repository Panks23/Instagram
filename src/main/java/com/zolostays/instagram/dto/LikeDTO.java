package com.zolostays.instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LikeDTO {

    private Long id;

    private PostDTO post_DTO;

    private UserDTO user_DTO;

    private Timestamp time_stamp;
}
