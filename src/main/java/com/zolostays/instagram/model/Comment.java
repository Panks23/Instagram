package com.zolostays.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String comment;

    @ManyToOne
    @JoinColumn(name="fk_post", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name="fk_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name="fk_comment", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment commentId;

    @NotNull
    private Timestamp timeStamp;
}
