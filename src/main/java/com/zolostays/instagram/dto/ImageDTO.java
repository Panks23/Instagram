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
public class ImageDTO {

    private Long id;
    private String image_url;
    private Timestamp timestamp;
}
