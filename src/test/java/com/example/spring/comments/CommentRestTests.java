package com.example.spring.comments;

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
public class CommentRestTests {
    @Autowired
    CommentService commentService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testGetAllReturnEmptyArray() {
        //Arrange

        //Act
        Comment[] responseComments = testRestTemplate.getForObject("/comments",Comment[].class);

        //Assert
        Assertions.assertEquals(0,responseComments.length);
    }

    @Test
    public void testCreate() {
        //Arrange
        Comment requestComment = new Comment(1L,"body1","authorName1");

        //Act
        Comment responseComment = testRestTemplate.postForObject("/comments",requestComment,Comment.class);

        //Assert
        Assertions.assertEquals(requestComment.getBody(),responseComment.getBody());
        Assertions.assertEquals(requestComment.getAuthorName(),responseComment.getAuthorName());

        // check that Comment is added
        Comment getResponseByArticleId = testRestTemplate.getForObject("/comments/" + responseComment.getId().toString(),Comment.class);
        Assertions.assertEquals(requestComment.getBody(), getResponseByArticleId.getBody());
        Assertions.assertEquals(requestComment.getAuthorName(),getResponseByArticleId.getAuthorName());

        //Clean up
        testRestTemplate.delete("/comments"+responseComment.getId().toString());
    }

    @Test
    public void testUpdate() {
        //Arrange
        Comment originalComment = commentService.create(new Comment(1L,"body1","authorName1"));

        //Act
        Comment updatedComment = new Comment(originalComment.getId(),"updated body","updated author name");
        Comment responseComment = putForComment(updatedComment);

        //Assert
        Assertions.assertEquals(updatedComment.getBody(),responseComment.getBody());
        Assertions.assertEquals(updatedComment.getAuthorName(),responseComment.getAuthorName());
    }

    @Test
    public void testDelete() {
        //Arrange
        Comment comment1 = commentService.create(new Comment(1L,"body1","authorName1"));
        Comment comment2 = commentService.create(new Comment(2L,"body2","authorName2"));

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        //Act
        testRestTemplate.delete("/comments"+comment1.getId().toString());

        //Assert
        Assertions.assertEquals(1,comments.size()-1);

    }

    //Common method to use
    private Comment putForComment(Comment requestBody) {
        HttpEntity<Comment> requestEntity = new HttpEntity<>(requestBody);
        HttpEntity<Comment> response = testRestTemplate.exchange("/comments", HttpMethod.PUT,requestEntity,Comment.class);
        return response.getBody();
    }
}
