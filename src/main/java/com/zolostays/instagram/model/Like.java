package com.zolostays.instagram.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="like_table")
@Entity
public class Like {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="fk_post", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post postId;

    @ManyToOne
    @JoinColumn(name="fk_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @CreationTimestamp
    private Timestamp timeStamp;
}
