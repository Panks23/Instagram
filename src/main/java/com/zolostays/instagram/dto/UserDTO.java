package com.zolostays.instagram.dto;


import com.zolostays.instagram.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO {

    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private Date dob;
    private Gender gender;
    private int phoneNo;
    private Timestamp timestamp;
}
