package com.zolostays.instagram.dto;

import com.zolostays.instagram.model.User;
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
    private User user;
    private Timestamp edited_at;

}
