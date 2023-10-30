package com.framed.imagesortingapi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String imageUrl;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "image_keyword",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private List<Keyword> keywords = new ArrayList<>();

    // Constructors
    public Image() {}

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Useful functions
    public void addKeyword(Keyword keyword) {
        if (keyword != null && !keywords.contains(keyword)) {
            this.keywords.add(keyword);
            keyword.getImages().add(this);
        }
    }
    public boolean removeKeyword(Keyword keyword) {
        keywords.remove(keyword);
        return keyword.removeImagem(this);
    }

    // Getters and setters
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

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image image)) return false;
        return Objects.equals(getId(), image.getId()) && Objects.equals(getImageUrl(), image.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}