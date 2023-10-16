package com.framed.imagesorter.repository;

import com.framed.imagesorter.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    boolean existsByName(String name);
    Optional<Keyword> findByName(String name);

}
