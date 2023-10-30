package com.framed.imagesortingapi.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.framed.imagesortingapi.controller.exception.ImageExtractingException;
import com.framed.imagesortingapi.controller.exception.ImageIOException;
import com.framed.imagesortingapi.model.Image;
import com.framed.imagesortingapi.model.ImageDTO;
import com.framed.imagesortingapi.model.Keyword;
import com.framed.imagesortingapi.repository.ImageRepository;
import com.framed.imagesortingapi.service.ImageService;
import com.framed.imagesortingapi.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final KeywordService keywordService;
    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, KeywordService keywordService) {
        this.imageRepository = imageRepository;
        this.keywordService = keywordService;
    }

    // Inherited methods from the interface
    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<ImageDTO> findAllImages() {
        return imageRepository.findAll().stream().map(ImageDTO::new).toList();
    }

    @Override
    public ImageDTO registerNewImage(String url) {
        Image imageBeingAdded = createImage(url);
        List<String> keywordsAsString = getAllKeywordsFromUrl(url);

        for (String keywordName : keywordsAsString) {
            imageBeingAdded.addKeyword(keywordService.createKeyword(keywordName));
        }

        imageRepository.save(imageBeingAdded);
        return new ImageDTO(imageBeingAdded);
    }

    @Override
    public ImageDTO addKeywordsToImage(Long id, String[] keywords) {
        Image selectedImage = findById(id);
        Arrays.asList(keywords).forEach(keyword -> {
            Keyword kw = keywordService.createKeyword(keyword);
            selectedImage.addKeyword(kw);
        });
        return new ImageDTO(imageRepository.save(selectedImage));
    }

    @Override
    public ImageDTO removeKeywordsFromImage(Long id, String[] keywords) {
        Image selectedImage = findById(id);
        Arrays.asList(keywords).forEach(keyword -> {
            Keyword kw = keywordService.createKeyword(keyword);
            selectedImage.removeKeyword(kw);
            imageRepository.save(selectedImage);
            if (kw.getImages().isEmpty()) {
                keywordService.deleteKeyword(kw.getId());
            }
        });
        return new ImageDTO(selectedImage);
    }

    @Override
    public void deleteImage(Long id) {
        Image imageToDelete = findById(id);
        List<Keyword> keywordsToCheck = imageToDelete.getKeywords();
        imageRepository.delete(imageToDelete);
        for (Keyword k : keywordsToCheck) {
            if (k.getImages().size() == 1) {
                keywordService.deleteKeyword(k.getId());
            }
        }
    }


    // OTHER METHODS

    // method to persist the image in the database but without any keywords associated.
    private Image createImage(String url) {
        if (imageRepository.existsByImageUrl(url) || !isUrlValid(url)) {
            throw new IllegalArgumentException("This image URL is not valid.");
        }
        return new Image(url);
    }

    // method used to extract the metadata from the image received by its url.
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

    // method to extract just the keywords from the image metadata.
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

    // method to check if the url is valid or not.
    private boolean isUrlValid(String url) {
        try {
            new URL(url);
            return true;
        } catch (java.net.MalformedURLException e) {
            return false;
        }
    }
}