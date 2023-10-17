package com.framed.imagesorter.controller;

import com.framed.imagesorter.model.Image;
import com.framed.imagesorter.model.ImageDTO;
import com.framed.imagesorter.model.Keyword;
import com.framed.imagesorter.service.ImageService;
import com.framed.imagesorter.service.KeywordService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;
    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> findImageById(@PathVariable Long id) {
        ImageDTO imageFound = new ImageDTO(imageService.findById(id));
        return ResponseEntity.ok(imageFound);
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> findAll() {
        return ResponseEntity.ok(imageService.findAllImages());
    }

    @PostMapping("/register")
    public ResponseEntity<ImageDTO> registerNewImage(@RequestBody String url) {
        ImageDTO imageRegistered = imageService.registerNewImage(url);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(imageRegistered.getId())
                .toUri();
        return ResponseEntity.created(location).body(imageRegistered);
    }

    @PutMapping("/add-keyword/{id}")
    public ResponseEntity<ImageDTO> addKeywordToImage(@RequestBody String[] keywords, @PathVariable Long id) {
        ImageDTO updatedImage = imageService.addKeywordsToImage(id, keywords);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

}
