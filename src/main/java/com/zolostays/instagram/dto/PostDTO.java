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
public class PostDTO {

    private Long id;
    private String caption;
    private ImageDTO imageDTO;
    private UserDTO userDTO;
    private Timestamp timestamp;
    private Timestamp edited_at;

}
