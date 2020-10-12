package com.zolostays.instagram.model;

import com.zolostays.instagram.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.UniqueElements;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column(length = 64)
    @UniqueElements
    private String username;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int phoneNo;

    @CreationTimestamp
    private Timestamp timeStamp;

}
