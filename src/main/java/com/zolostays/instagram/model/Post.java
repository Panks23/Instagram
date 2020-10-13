package com.zolostays.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 64)
    private String caption;

    @ManyToOne
    @JoinColumn(name="fk_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy="post")
    private List<Image> imageList;


    @CreationTimestamp
    private Timestamp timeStamp;

    @Column(nullable = false)
    private Timestamp edited_at;


}
