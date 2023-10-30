package com.framed.imagesortingapi.service.impl;

import com.framed.imagesortingapi.model.Image;
import com.framed.imagesortingapi.model.ImageDTO;
import com.framed.imagesortingapi.model.Keyword;
import com.framed.imagesortingapi.repository.ImageRepository;
import com.framed.imagesortingapi.repository.KeywordRepository;
import com.framed.imagesortingapi.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    private final ImageRepository imageRepository;

    @Autowired
    public KeywordServiceImpl(KeywordRepository keywordRepository, ImageRepository imageRepository) {
        this.keywordRepository = keywordRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Keyword createKeyword(String name) {
        Optional<Keyword> existingKeyword = keywordRepository.findByName(name);

        if (existingKeyword.isPresent()) {
            return existingKeyword.get();
        } else {
            Keyword keywordAdded = new Keyword(name);
            return keywordRepository.save(keywordAdded);
        }
    }

    @Override
    public Keyword findByName(String name) {
        return keywordRepository.findByName(name).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Keyword findById(Long id) {
        return keywordRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<ImageDTO> getImagesByKeyword(String keywordName) {
        return findByName(keywordName).getImages().stream().map(ImageDTO::new).toList();
    }

    @Override
    public void deleteKeyword(Long id) {
        Keyword keywordToDelete = findById(id);

        List<Image> imagesToRemove = new ArrayList<>(keywordToDelete.getImages());

        for (Image img : imagesToRemove) {
            img.removeKeyword(keywordToDelete);
            imageRepository.save(img);
        }

        keywordRepository.delete(keywordToDelete);
    }

    @Override
    public List<Keyword> getAllKeywords() {
        return keywordRepository.findAll();
    }

    public void deleteAllKeywords(List<Keyword> keywords) {
        this.keywordRepository.deleteAll(keywords);
    }

}
