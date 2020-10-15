package com.zolostays.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors(chain = true)
public class FollowMap {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="followed_by", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followedBy;

    @ManyToOne
    @JoinColumn(name="followed_to", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followedTo;

    @CreationTimestamp
    private Timestamp timeStamp;

}
