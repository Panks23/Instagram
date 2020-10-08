package com.zolostays.instagram.model;

import com.sun.istack.NotNull;
import com.zolostays.instagram.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {

    @Id
    @Column(length = 64)
    private String username;
    @NotNull
    private String first_name;
    @NotNull
    private String last_name;
    private Date dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int phoneNo;
    private Timestamp timeStamp;

}
