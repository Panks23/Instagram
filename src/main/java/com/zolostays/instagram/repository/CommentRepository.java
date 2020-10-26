package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Comment;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByCommentId(Comment comment);

    List<Comment> findAllByPostAndCommentIdIsNull(Post post);

    Optional<Comment> findByIdAndUserAndPostAndCommentIdIsNull(Long comment_id, User user, Post post);

    Optional<Comment> findByIdAndPostAndCommentIdIsNull(Long comment_id, Post post);

    Optional<Comment> findByIdAndPostAndUserAndCommentId(Long reply_id, Post post, User user, Comment parent_comment);

    boolean existsByIdAndUserAndPostAndCommentIdIsNull(Long comment_id, User user, Post post);

    void deleteByIdAndUserAndPostAndCommentIdIsNull(Long comment_id, User user, Post post);

    boolean existsByIdAndUserAndPostAndCommentId(Long comment_id, User user, Post post, Comment parent_comment);

    void deleteByIdAndUserAndPostAndCommentId(Long comment_id, User user, Post post, Comment parent_comment);

}
