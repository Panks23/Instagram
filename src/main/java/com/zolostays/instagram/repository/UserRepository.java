package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

            Boolean existsByUsername(String username);
}
