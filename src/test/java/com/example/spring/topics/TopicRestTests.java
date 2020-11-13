package com.example.spring.topics;

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
public class TopicRestTests {
    @Autowired
    TopicService topicService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testGetAllReturnEmptyArray() {
        //Arrange

        //Act
        Topic[] responseTopics = testRestTemplate.getForObject("/topics",Topic[].class);

        //Assert
        Assertions.assertEquals(0,responseTopics.length);
    }

    @Test
    public void testCreate() {
        //Arrange
        Topic requestTopic = new Topic(1L,"name1");

        //Act
        Topic responseTopic = testRestTemplate.postForObject("/topics",requestTopic,Topic.class);

        //Assert
        Assertions.assertEquals(requestTopic.getName(),responseTopic.getName());
        Assertions.assertEquals(requestTopic.getArticles(),responseTopic.getArticles());

        // check that Topic is added
        Topic getResponseByTopicId = testRestTemplate.getForObject("/topics/" + responseTopic.getId().toString(),Topic.class);
        Assertions.assertEquals(requestTopic.getName(), getResponseByTopicId.getName());

        //Clean up
        testRestTemplate.delete("/topics"+responseTopic.getId().toString());
    }

    @Test
    public void testUpdate() {
        //Arrange
        Topic originalTopic = topicService.create(new Topic(1L,"name1"));

        //Act
        Topic updatedTopic = new Topic(originalTopic.getId(),"updated author name");
        Topic responseTopic = putForTopic(updatedTopic);

        //Assert
        Assertions.assertEquals(updatedTopic.getName(),responseTopic.getName());
    }

    @Test
    public void testDelete() {
        //Arrange
        Topic topic1 = topicService.create(new Topic(1L,"name1"));
        Topic topic2 = topicService.create(new Topic(2L,"name2"));

        List<Topic> topics = new ArrayList<>();
        topics.add(topic1);
        topics.add(topic2);
        //Act
        testRestTemplate.delete("/topics"+topic1.getId().toString());

        //Assert
        Assertions.assertEquals(1,topics.size()-1);

    }

    //Common method to use
    private Topic putForTopic(Topic requestBody) {
        HttpEntity<Topic> requestEntity = new HttpEntity<>(requestBody);
        HttpEntity<Topic> response = testRestTemplate.exchange("/topics", HttpMethod.PUT,requestEntity,Topic.class);
        return response.getBody();
    }
}
