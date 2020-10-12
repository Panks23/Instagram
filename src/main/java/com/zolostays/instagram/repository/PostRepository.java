package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
