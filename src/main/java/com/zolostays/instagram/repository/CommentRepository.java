package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
