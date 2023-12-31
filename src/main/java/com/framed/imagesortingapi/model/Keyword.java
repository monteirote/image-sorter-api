package com.framed.imagesortingapi.model;

import com.framed.imagesortingapi.repository.KeywordRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword_name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "keywords", fetch = FetchType.EAGER)
    private List<Image> images = new ArrayList<>();

    public Keyword() {}

    public Keyword(String name) {
        this.name = name;
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

    public List<Image> getImages() {
        return images;
    }

    public boolean removeImagem(Image imageToDelete) {
        this.getImages().remove(imageToDelete);
        return getImages().isEmpty();
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Keyword keyword)) return false;
        return Objects.equals(getId(), keyword.getId()) && Objects.equals(getName(), keyword.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
