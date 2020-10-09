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
public class Like_table {

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

    @NotNull
    private Timestamp timeStamp;
}