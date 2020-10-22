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
public class FollowMapDTO {

    private Long id;

    private User followedBy;

    private User followedTo;

    private Timestamp timeStamp;
}
