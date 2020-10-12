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
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="fk_post")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Post post;

    @NotNull
    private String image_url;

    @CreationTimestamp
    private Timestamp timeStamp;


}
