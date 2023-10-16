package com.framed.imagesorter.service.impl;

import com.framed.imagesorter.model.Keyword;
import com.framed.imagesorter.repository.KeywordRepository;
import com.framed.imagesorter.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordServiceImpl(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public Keyword createKeyword(String name) {
        if (keywordRepository.findByName(name).isPresent()) {
            return keywordRepository.findByName(name).get();
        } else {
            Keyword keyword = new Keyword(name);
            keywordRepository.save(keyword);
            return keyword;
        }
    }

    public Optional<Keyword> findByName(String name) {
        return keywordRepository.findByName(name);
    }

}
