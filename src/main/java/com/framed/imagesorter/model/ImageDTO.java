package com.framed.imagesorter.model;

import java.util.List;
import java.util.Objects;

public class ImageDTO {

    private Long id;
    private String imageUrl;
    private List<String> keywords;

    public ImageDTO(Image image) {
        this.id = image.getId();
        this.imageUrl = image.getImageUrl();
        this.keywords = keywordToString(image.getKeywords());
    }

    private List<String> keywordToString(List<Keyword> keywords) {
        return keywords.stream().map(Keyword::getName).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageDTO imageDTO)) return false;
        return Objects.equals(getId(), imageDTO.getId()) && Objects.equals(getImageUrl(), imageDTO.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getImageUrl());
    }
}
