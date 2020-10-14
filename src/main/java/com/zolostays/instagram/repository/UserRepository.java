package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

            Boolean existsByUsername(String username);
}
