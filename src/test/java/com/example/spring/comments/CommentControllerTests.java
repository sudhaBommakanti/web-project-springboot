package com.example.spring.comments;

import com.example.spring.articles.Article;
import com.example.spring.articles.ArticleController;
import com.example.spring.articles.ArticleService;
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
public class CommentControllerTests {
    @Autowired
    CommentController commentController;

    @MockBean
    CommentService commentService;

    @Test
    public void testGetById() {
        //Arrange

        Comment comment1 = new Comment(1L,"body1","author1");
        Comment comment2 = new Comment(2L,"body2","author2");

        Mockito.when(commentService.getById(1L))
                .thenReturn(Optional.of(comment1));
        Mockito.when(commentService.getById(2L))
                .thenReturn(Optional.of(comment2));
        //Act
        Comment controllerComment1 = commentController.getById(1L);
        Comment controllerComment2 = commentController.getById(2L);

        //Assert
        Assertions.assertEquals(comment1.getAuthorName(),controllerComment1.getAuthorName());
        Assertions.assertEquals(comment2.getBody(),controllerComment2.getBody());

        Assertions.assertEquals(comment1.getArticle(),controllerComment1.getArticle());
    }

    @Test
    public void testGetAllByArticleId() {
        // Arrange
        Long articleId = 101L;

        Comment comment1 = new Comment(1L,"body1","author1");
        Comment comment2 = new Comment(2L,"body2","author2");
        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        Mockito.when(commentService.getAllByArticleId(articleId))
                .thenReturn(comments);

        // Act
        List<Comment> actualComments = commentController.getAll(articleId);

        // Assert
        Assertions.assertEquals(comments.size(), actualComments.size());
        Assertions.assertEquals(comments.get(0).getAuthorName(), actualComments.get(0).getAuthorName());
        Assertions.assertEquals(comments.get(1).getBody(), actualComments.get(1).getBody());
    }
}
