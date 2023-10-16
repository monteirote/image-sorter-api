package com.framed.imagesorter.repository;

import com.framed.imagesorter.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    boolean existsByImageUrl(String url);
    Optional<Image> findByImageUrl(String url);
}
