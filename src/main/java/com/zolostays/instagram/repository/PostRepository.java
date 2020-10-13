package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);

}
