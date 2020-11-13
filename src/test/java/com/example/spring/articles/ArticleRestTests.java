package com.example.spring.articles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleRestTests {
    @Autowired
    ArticleService articleService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testGetAllReturnEmptyArray() {
        //Arrange

        //Act
        Article[] responseArticles = testRestTemplate.getForObject("/articles",Article[].class);

        //Assert
        Assertions.assertEquals(0,responseArticles.length);
    }

    @Test
    public void testCreate() {
        //Arrange
        Article requestArticle = new Article(1L,"title1","body1","authorName1");

        //Act
        Article responseArticle = testRestTemplate.postForObject("/articles",requestArticle,Article.class);

        //Assert
        Assertions.assertEquals(requestArticle.getTitle(),responseArticle.getTitle());
        Assertions.assertEquals(requestArticle.getAuthorName(),responseArticle.getAuthorName());

        // check that Article is added
        Article getResponseByArticleId = testRestTemplate.getForObject("/articles/" + responseArticle.getId().toString(),Article.class);
        Assertions.assertEquals(requestArticle.getTitle(), getResponseByArticleId.getTitle());
        Assertions.assertEquals(requestArticle.getAuthorName(),getResponseByArticleId.getAuthorName());

        //Clean up
        testRestTemplate.delete("/articles"+responseArticle.getId().toString());
    }

    @Test
    public void testUpdate() {
        //Arrange
        Article originalArticle = articleService.create(new Article(1L,"title1","body1","authorName1"));

        //Act
        Article updatedArticle = new Article(originalArticle.getId(),"updated title","updated body","updated author name");
        Article responseArticle = putForArticle(updatedArticle);

        //Assert
        Assertions.assertEquals(updatedArticle.getTitle(),responseArticle.getTitle());
        Assertions.assertEquals(updatedArticle.getAuthorName(),responseArticle.getAuthorName());
        Assertions.assertEquals(updatedArticle.getBody(),responseArticle.getBody());

    }

    @Test
    public void testDelete() {
        //Arrange
        Article article1 = articleService.create(new Article(1L,"title1","body1","authorName1"));
        Article article2 = articleService.create(new Article(2L,"title2","body2","authorName2"));

        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

        //Act
        testRestTemplate.delete("/articles"+article1.getId().toString());

        //Assert
        Assertions.assertEquals(1,articles.size()-1);

    }

    //Common method to use
    private Article putForArticle(Article requestBody) {
        HttpEntity<Article> requestEntity = new HttpEntity<>(requestBody);
        HttpEntity<Article> response = testRestTemplate.exchange("/articles", HttpMethod.PUT,requestEntity,Article.class);
        return response.getBody();
    }
}
