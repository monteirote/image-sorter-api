package com.framed.imagesorter.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.framed.imagesorter.controller.exception.ImageIOException;
import com.framed.imagesorter.controller.exception.ImageExtractingException;
import com.framed.imagesorter.model.Image;
import com.framed.imagesorter.model.Keyword;
import com.framed.imagesorter.repository.ImageRepository;
import com.framed.imagesorter.service.ImageService;
import com.framed.imagesorter.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final KeywordService keywordService;
    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, KeywordService keywordService) {
        this.imageRepository = imageRepository;
        this.keywordService = keywordService;
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Image registerNewImage(String url) {
        Image imageBeingAdded = createImage(url);
        List<String> keywordsAsString = getAllKeywordsFromUrl(url);

        for (String keywordName : keywordsAsString) {
            imageBeingAdded.addKeyword(keywordService.createKeyword(keywordName));
        }

        imageRepository.save(imageBeingAdded);
        return imageBeingAdded;
    }

    private Metadata extractMetadataFromImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return ImageMetadataReader.readMetadata(url.openStream());
        } catch (IOException error) {
            throw new ImageIOException("Erro de E/S ao abrir a URL da imagem: " + imageUrl, error);
        } catch (ImageProcessingException error) {
            throw new ImageExtractingException("Erro ao processar a imagem: " + imageUrl, error);
        }
    }

    private List<String> getAllKeywordsFromUrl(String imageUrl) {
        Metadata metadata = extractMetadataFromImage(imageUrl);

        for (Directory dir : metadata.getDirectories()) {
            for (Tag tag : dir.getTags()) {
                if (tag.getTagName().equals("Keywords")) {
                    return Arrays.asList(tag.getDescription().split(";"));
                }
            }
        }

        return Collections.emptyList();
    }





    @Override
    public Image addKeywordsToImage(Long id, String[] keywords) {
        Image selectedImage = findById(id);
        Arrays.asList(keywords).forEach(keyword -> {
            Keyword kw = keywordService.createKeyword(keyword);
            selectedImage.addKeyword(kw);
        });
        return imageRepository.save(selectedImage);
    }

    @Override
    public void deleteImage(Long id) {
        Image imageToDelete = findById(id);
        imageRepository.delete(imageToDelete);
    }

    private boolean isUrlValid(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (java.net.MalformedURLException e) {
            return false;
        }
    }

    private Image createImage(String url) {
        if (imageRepository.existsByImageUrl(url) && !isUrlValid(url)) {
            throw new IllegalArgumentException("This image URL is not valid.");
        }
        return imageRepository.save(new Image(url));
    }
}
