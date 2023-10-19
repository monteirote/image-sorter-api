package com.framed.imagesorter.controller;

import com.framed.imagesorter.model.ImageDTO;
import com.framed.imagesorter.repository.ImageRepository;
import com.framed.imagesorter.service.ImageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageControllerUnitTest {

    @Autowired
    private ImageController imageController;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    ResponseEntity<ImageDTO> creatingImageToTest() {
        return imageController.registerNewImage("https://cdn.46graus.com/files/portfolio/2609/e02c60b4-22fe-4888-b850-8bfe615a3053/300_f446ec3a-cbef-4b57-a402-69fa71306835.jpg");
    }
    @AfterEach
    void cleanDatabase() {
        imageRepository.deleteAll();
    }

    @Test
    void registerNewImage() {
        ResponseEntity<ImageDTO> imageRegistered = creatingImageToTest();
        assertNotNull(imageRegistered.getBody());
        ImageDTO imageFound = imageController.findImageById(imageRegistered.getBody().getId()).getBody();
        assertEquals(imageFound, imageRegistered.getBody());
    }
    @Test
    void findImageById() {
        ResponseEntity<ImageDTO> imageRegistered = creatingImageToTest();
        assertNotNull(imageRegistered.getBody());
        ResponseEntity<ImageDTO> imageFound = imageController.findImageById(imageRegistered.getBody().getId());
        assertNotNull(imageFound.getBody());
        assertEquals(imageRegistered.getBody(), imageFound.getBody());
    }

    @Test
    void findAll() {
        creatingImageToTest();
        List<ImageDTO> allImages = imageController.findAll().getBody();
        assertNotNull(allImages);
        assertEquals(1, allImages.size());
    }

    @Test
    void addKeywordToImage() {
        ResponseEntity<ImageDTO> imageRegistered = creatingImageToTest();
        assertNotNull(imageRegistered.getBody());
        String[] keywordsToAdd = {"test", "test2", "test3"};
        imageRegistered = imageController.addKeywordToImage(keywordsToAdd, imageRegistered.getBody().getId());
        assertNotNull(imageRegistered.getBody());
        System.out.println(imageRegistered.getBody().getKeywords());
        assertTrue(imageRegistered.getBody().getKeywords().contains("test"));
        assertTrue(imageRegistered.getBody().getKeywords().contains("test2"));
        assertTrue(imageRegistered.getBody().getKeywords().contains("test3"));
        assertFalse(imageRegistered.getBody().getKeywords().contains("test4"));
    }

    @Test
    void deleteImage() {
        ResponseEntity<ImageDTO> imageRegistered = creatingImageToTest();
        assertNotNull(imageRegistered.getBody());
        imageController.deleteImage(imageRegistered.getBody().getId());
        assertThrows(NoSuchElementException.class, () -> {
            imageController.findImageById(imageRegistered.getBody().getId());
        });
    }
}