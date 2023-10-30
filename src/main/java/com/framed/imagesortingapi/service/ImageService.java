package com.framed.imagesortingapi.service;

import com.framed.imagesortingapi.model.Image;
import com.framed.imagesortingapi.model.ImageDTO;

import java.util.List;

public interface ImageService {

    Image findById(Long id);

    List<ImageDTO> findAllImages();

    ImageDTO registerNewImage(String url);

    ImageDTO addKeywordsToImage(Long id, String[] keywords);

    ImageDTO removeKeywordsFromImage(Long id, String[] keywords);

    void deleteImage(Long id);
}