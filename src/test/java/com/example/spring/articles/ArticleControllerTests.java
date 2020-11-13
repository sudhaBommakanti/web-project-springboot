package com.example.spring.articles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ArticleControllerTests {
    @Autowired
    ArticleController articleController;

    @MockBean
    ArticleService articleService;

    @Test
    public void testGetById() {
        //Arrange
        Article article1 = new Article(1L,"title1","body1","author1");
        Article article2 = new Article(2L,"title2","body2","author2");
        Mockito.when(articleService.getById(1L))
                .thenReturn(Optional.of(article1));
        Mockito.when(articleService.getById(2L))
                .thenReturn(Optional.of(article2));
        //Act
        Article controllerArticle1 = articleController.getById(1L);
        Article controllerArticle2 = articleController.getById(2L);

        //Assert
        Assertions.assertEquals(article1.getTitle(),controllerArticle1.getTitle());
        Assertions.assertEquals(article2.getAuthorName(),controllerArticle2.getAuthorName());

        Assertions.assertEquals(article1.getBody(),controllerArticle1.getBody());
    }

    @Test
    public void testGetAllByTopicId() {
        // Arrange
        Long topicId = 101L;
        Article article1 = new Article(1L,"title1","body1","author1");
        Article article2 = new Article(2L,"title2","body2","author2");
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        Mockito.when(articleService.getAllByTopicId(topicId))
                .thenReturn(articles);

        // Act
        List<Article> actualArticles = articleController.getAll(topicId);

        // Assert
        Assertions.assertEquals(articles.size(), actualArticles.size());
        Assertions.assertEquals(articles.get(0).getAuthorName(), actualArticles.get(0).getAuthorName());
        Assertions.assertEquals(articles.get(1).getTitle(), actualArticles.get(1).getTitle());
    }
}
