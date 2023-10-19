package com.framed.imagesorter;

import com.framed.imagesorter.model.Image;
import com.framed.imagesorter.model.Keyword;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageAndKeywordTests {

    Image imageTest;
    Keyword keywordTest;
    @BeforeEach
    void creatingImageAndKeywordForTests() {
        imageTest = new Image();
        keywordTest = new Keyword("KeywordTest");
    }
    @Test
    void testingConstructors() {
        assertNotNull(imageTest);
        assertNotNull(keywordTest);
    }
    @Test
    void testingImage_AddKeywords() {
        imageTest.addKeyword(new Keyword("keywordTest"));
        assertEquals(1, imageTest.getKeywords().size());

        imageTest.addKeyword(new Keyword("keywordTest"));
        assertEquals(1, imageTest.getKeywords().size());
    }
    @Test
    void testingImage_RemoveKeywords() {
        Image img1 = new Image();
        Keyword keyword1 = new Keyword("keywordTest");

        img1.addKeyword(keyword1);
        img1.addKeyword(null);
        assertTrue(img1.getKeywords().contains(keyword1) && keyword1.getImages().contains(img1));
        assertFalse(img1.getKeywords().contains(keyword1) && keyword1.getImages().contains(null));

        img1.removeKeyword(keyword1);
        assertTrue(img1.getKeywords().isEmpty());
    }

    @Test
    void testingImage_Equals() {
        imageTest.setImageUrl("test-url");
        Image imageTest2 = new Image("test-url");
        assertEquals(imageTest, imageTest2);
    }

    @Test
    void testingKeyword_Equals() {
        Keyword keywordTest2 = new Keyword("KeywordTest");
        assertEquals(keywordTest, keywordTest2);
    }
}
