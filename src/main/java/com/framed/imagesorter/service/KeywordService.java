package com.framed.imagesorter.service;

import com.framed.imagesorter.model.ImageDTO;
import com.framed.imagesorter.model.Keyword;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface KeywordService {

    Keyword createKeyword(String name);

    Keyword findByName(String name);

    Keyword findById(Long id);

    List<ImageDTO> getImagesByKeyword(String keywordName);

    void deleteKeyword(Long id);

}
