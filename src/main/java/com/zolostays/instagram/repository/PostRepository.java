package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);

    Optional<Post> findByIdAndUser(Long post_id, User user);

    boolean existsByIdAndUser(Long post_id, User user);

    void deleteByIdAndUser(Long post_id, User user);

}
