package com.framed.imagesorter.service;

import com.framed.imagesorter.model.Keyword;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface KeywordService {

    Keyword createKeyword(String name);

    Optional<Keyword> findByName(String name);
}
