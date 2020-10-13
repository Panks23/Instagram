package com.zolostays.instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PostDTO {

    private Long id;
    private String caption;
    private List<ImageDTO> listImageDTO;
    private UserDTO userDTO;
    private Timestamp timestamp;
    private Timestamp edited_at;

}
