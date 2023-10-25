package com.framed.imagesortingapi.repository;

import com.framed.imagesortingapi.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    boolean existsByImageUrl(String url);
    Optional<Image> findByImageUrl(String url);
}

