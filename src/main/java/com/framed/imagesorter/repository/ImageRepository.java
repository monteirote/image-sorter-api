package com.framed.imagesorter.repository;

import com.framed.imagesorter.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
