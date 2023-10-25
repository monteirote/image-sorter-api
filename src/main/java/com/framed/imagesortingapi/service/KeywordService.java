package com.framed.imagesortingapi.service;

import com.framed.imagesortingapi.model.ImageDTO;
import com.framed.imagesortingapi.model.Keyword;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KeywordService {

    Keyword createKeyword(String name);

    Keyword findByName(String name);

    Keyword findById(Long id);

    List<ImageDTO> getImagesByKeyword(String keywordName);

    void deleteKeyword(Long id);

}
