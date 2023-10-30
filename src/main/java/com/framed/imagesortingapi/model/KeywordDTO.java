package com.framed.imagesortingapi.model;

import java.util.List;

public class KeywordDTO {

    private Long id;

    private String name;

    private List<Long> imagesIds;

    public KeywordDTO(Keyword keyword) {
        this.id = keyword.getId();
        this.name = keyword.getName();
        this.imagesIds = keyword.getImages().stream().map(Image::getId).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getImagesIds() {
        return imagesIds;
    }

    public void setImagesIds(List<Long> imagesIds) {
        this.imagesIds = imagesIds;
    }
}
