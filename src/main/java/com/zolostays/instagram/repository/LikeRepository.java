package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Like;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
            List<Like> findAllByPostId(Post post);
            void deleteByPostIdAndUser(Post post, User user);
            boolean existsByUserAndPostId(User usre, Post post);
}
