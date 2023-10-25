package com.framed.imagesortingapi.service;

import com.framed.imagesortingapi.model.Image;
import com.framed.imagesortingapi.model.Keyword;
import com.framed.imagesortingapi.model.ImageDTO;
import com.framed.imagesortingapi.repository.ImageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageServiceUnitTest {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    private final String exampleValidImageUrl = "https://cdn.46graus.com/files/portfolio/2609/e02c60b4-22fe-4888-b850-8bfe615a3053/original_f446ec3a-cbef-4b57-a402-69fa71306835.jpg";
    private final String exampleInvalidImageUrl = "invalid-image-url";

    @AfterEach
    void resetDatabase() {
        imageRepository.deleteAll();
    }

    @Test
    void testFindById_Success() {
        Image imageRegistered = imageRepository.save(new Image("test-url"));
        Image imageFound = imageService.findById(imageRegistered.getId());
        assertNotNull(imageFound);
        assertEquals("test-url", imageFound.getImageUrl());
    }

    @Test
    void testFindById_Error() {
        assertThrows(NoSuchElementException.class, () -> {
            imageService.findById(1L);
        });
    }

    @Test
    void testFindAllImages_WithContent() {
        imageRepository.save(new Image("test-image1"));
        imageRepository.save(new Image("test-image2"));
        assertEquals(2, imageService.findAllImages().size());

        imageRepository.save(new Image("test-image3"));
        assertEquals(3, imageService.findAllImages().size());
    }

    @Test
    void testFindAllImages_NoContent() {
        assertEquals(0, imageService.findAllImages().size());
    }

    @Test
    void testRegisterNewImage_Success() {
        Long id = imageService.registerNewImage(exampleValidImageUrl).getId();
        Optional<Image> imageFound = imageRepository.findById(id);
        String[] keywordsExpected = { "coliseu", "frejat", "show", "una" };
        assertTrue(imageFound.isPresent());
        assertTrue(imageFound.get().getKeywords().stream().map(Keyword::getName).toList().containsAll(List.of(keywordsExpected)));
    }

    @Test
    void testRegisterNewImage_InvalidUrl() {
        assertThrows(IllegalArgumentException.class, () -> {
            imageService.registerNewImage(exampleInvalidImageUrl);
        });
    }

    @Test
    void testRegisterNewImage_AlreadyExists() {
        imageService.registerNewImage(exampleValidImageUrl);
        assertThrows(IllegalArgumentException.class, () -> {
            imageService.registerNewImage(exampleValidImageUrl);
        });
    }

    @Test
    void testAddKeywordsToImage_Success() {
        ImageDTO imageRegistered = imageService.registerNewImage(exampleValidImageUrl);
        imageService.addKeywordsToImage(imageRegistered.getId(), new String[]{"testKeyword", "testKeyword2"});
        String[] keywordsExpected = { "coliseu", "frejat", "show", "una", "testKeyword", "testKeyword2" };
        assertTrue(imageService.findById(imageRegistered.getId())
                .getKeywords().stream().map(Keyword::getName)
                .toList().containsAll(List.of(keywordsExpected)));
    }

    @Test
    void testAddKeywordsToImage_NotAdded() {
        ImageDTO imageRegistered = imageService.registerNewImage(exampleValidImageUrl);
        int sizeBeforeAdding = imageRegistered.getKeywords().size();
        imageService.addKeywordsToImage(imageRegistered.getId(), new String[]{"coliseu", "frejat", "show", "una"});
        assertEquals(sizeBeforeAdding, imageService.findById(imageRegistered.getId())
                .getKeywords().stream()
                .map(Keyword::getName)
                .toList().size());
    }

    @Test
    void testDeleteImage_Success() {
        ImageDTO imageRegistered = imageService.registerNewImage(exampleValidImageUrl);
        assertEquals(1, imageRepository.findAll().size());
        imageService.deleteImage(imageRegistered.getId());
        assertEquals(0, imageRepository.findAll().size());
    }

    @Test
    void testDeleteImage_UnknownID() {
        assertThrows(NoSuchElementException.class, () -> {
            imageService.deleteImage(3L);
        });
    }







}

