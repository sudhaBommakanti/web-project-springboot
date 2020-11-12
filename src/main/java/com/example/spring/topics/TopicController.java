package com.example.spring.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TopicController {
    private TopicService topicService;

    public TopicController(@Autowired TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public List<Topic> getAll(@RequestParam(required = false) Long articleId) {
        if(articleId != null) {
            return topicService.getAllByArticleId(articleId);
        } else {
            return topicService.getAll();
        }
    }

    @GetMapping("topics/{id}")
    public Topic getById(@PathVariable Long id) {
        return topicService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/topics")
    public Topic create(@RequestBody Topic topic) {
        return topicService.create(topic);
    }

    @PutMapping("/topics")
    public Topic update(@RequestBody Topic topic) {
        return topicService.update(topic);
    }

    @DeleteMapping("topics/{id}")
    public void delete(@PathVariable Long id) {
        topicService.delete(id);
    }
}

