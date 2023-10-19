package com.framed.imagesorter.controller;

import com.framed.imagesorter.model.Image;
import com.framed.imagesorter.repository.ImageRepository;
import com.framed.imagesorter.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ImageController.class)
class ImageControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageController imageController;

    private final String testImageUrl = "https://cdn.46graus.com/files/portfolio/2609/e02c60b4-22fe-4888-b850-8bfe615a3053/original_a5ea923e-b316-4179-bffa-5bddf2df0c04.jpg";

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void testFindImageByIdSuccess() throws Exception {
        Image testImageToSave = new Image(testImageUrl);
        Long idToSearch = imageRepository.save(testImageToSave).getId();
        mvc.perform(MockMvcRequestBuilders.get("/images/" + idToSearch)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(idToSearch))
                .andExpect(jsonPath("$.imageUrl").value("https://cdn.46graus.com/files/portfolio/2609/e02c60b4-22fe-4888-b850-8bfe615a3053/original_a5ea923e-b316-4179-bffa-5bddf2df0c04.jpg"))
                .andExpect(jsonPath("$.keywords", hasSize(5)))
                .andExpect(jsonPath("$.keywords", containsInAnyOrder("Fulltime", "Luiz Melodia", "Paula Lima", "nextel", "show")));
    }
}


