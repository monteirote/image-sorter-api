package com.framed.imagesorter.repository;

import com.framed.imagesorter.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
