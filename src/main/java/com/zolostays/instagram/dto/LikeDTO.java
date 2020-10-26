package com.zolostays.instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {

    private Long id;

    private PostDTO post_DTO;

    private UserDTO user_DTO;

    private Timestamp time_stamp;
}
