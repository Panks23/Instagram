package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Comment;
import com.zolostays.instagram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByCommentId(Comment comment);
    List<Comment> findAllByPostAndCommentIdIsNull(Post post);
}
