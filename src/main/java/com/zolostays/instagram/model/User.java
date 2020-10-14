package com.zolostays.instagram.model;

import com.zolostays.instagram.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="username", length = 64, unique = true)
    private String username;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int phoneNo;

    @CreationTimestamp
    private Timestamp timeStamp;

}
