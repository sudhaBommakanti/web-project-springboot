package com.example.spring.topics;

import com.example.spring.articles.ArticleController;
import com.example.spring.comments.Comment;
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
public class TopicControllerTests {
    @Autowired
    TopicController topicController;

    @MockBean
    private TopicService topicService;

    @Test
    public void testGetById() {
        //Arrange

        Topic topic1 = new Topic(1L,"name1");
        Topic topic2 = new Topic(2L,"name2");

        Mockito.when(topicService.getById(1L))
                .thenReturn(Optional.of(topic1));
        Mockito.when(topicService.getById(2L))
                .thenReturn(Optional.of(topic2));
        //Act
        Topic controllerTopic1 = topicController.getById(1L);
        Topic controllerTopic2 = topicController.getById(2L);

        //Assert
        Assertions.assertEquals(topic1.getName(),controllerTopic1.getName());
        Assertions.assertEquals(topic2.getName(),controllerTopic2.getName());
    }

    @Test
    public void testGetAllByArticleId() {
        // Arrange
        Long articleId = 101L;

        Topic topic1 = new Topic(1L,"name1");
        Topic topic2 = new Topic(2L,"body2");
        List<Topic> topics = new ArrayList<>();
        topics.add(topic1);
        topics.add(topic2);

        Mockito.when(topicService.getAllByArticleId(articleId))
                .thenReturn(topics);

        // Act
        List<Topic> actualTopics = topicController.getAll(articleId);

        // Assert
        Assertions.assertEquals(topics.size(), actualTopics.size());
        Assertions.assertEquals(topics.get(0).getName(), actualTopics.get(0).getName());
        Assertions.assertEquals(topics.get(1).getName(), actualTopics.get(1).getName());
    }
}
