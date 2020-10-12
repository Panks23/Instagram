package com.zolostays.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FollowMap {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="followed_by", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followed_by;

    @ManyToOne
    @JoinColumn(name="followed_to", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followerd_to;

    @CreationTimestamp
    private Timestamp timeStamp;

}
