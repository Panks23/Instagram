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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 64)
    private String username;

    @Column(nullable = false, unique = true)
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
