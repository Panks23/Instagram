package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.FollowMap;
import com.zolostays.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowMapRepository extends JpaRepository<FollowMap, Long> {

    void deleteByFollowedByAndFollowedTo(User followed_by, User followed_to);
    List<FollowMap> findAllByFollowedBy(User user);
    List<FollowMap> findAllByFollowedTo(User user);
}
