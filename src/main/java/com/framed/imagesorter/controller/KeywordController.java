package com.framed.imagesorter.controller;

import com.framed.imagesorter.model.ImageDTO;
import com.framed.imagesorter.service.KeywordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keywords")
public class KeywordController {
    private final KeywordService keywordService;
    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long id) {
        keywordService.deleteKeyword(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{keywordName}")
    public ResponseEntity<List<ImageDTO>> getImagesByKeyword(@PathVariable String keywordName) {
        return ResponseEntity.ok(keywordService.getImagesByKeyword(keywordName));
    }


}
