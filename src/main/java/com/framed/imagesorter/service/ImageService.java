package com.framed.imagesorter.service;

import com.drew.metadata.Metadata;
import com.framed.imagesorter.model.Image;
import com.framed.imagesorter.model.Keyword;
import com.framed.imagesorter.repository.ImageRepository;

import java.util.List;
import java.util.Map;

public interface ImageService {

    Image findById(Long id);
    Image registerNewImage(String url);



    Image addKeywordsToImage(Long id, String[] keywords);

    void deleteImage(Long id);







}
