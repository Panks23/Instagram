package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
