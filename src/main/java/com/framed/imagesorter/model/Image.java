package com.framed.imagesorter.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String imageUrl;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "image_keyword",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private List<Keyword> keywords;

    // Constructors
    public Image() {}

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
