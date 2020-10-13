package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Image;
import com.zolostays.instagram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
        void deleteByPost(Post post);
}
