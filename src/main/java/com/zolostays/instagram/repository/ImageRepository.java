package com.zolostays.instagram.repository;

import com.zolostays.instagram.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
