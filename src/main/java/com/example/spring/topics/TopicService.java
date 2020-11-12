package com.example.spring.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    private TopicRepo repo;

    public List<Topic> getAll() {
        return repo.findAll();
    }

    public Optional<Topic> getById(Long id) {
        return repo.findById(id);
    }

    public Topic create(Topic topic) {
        return repo.save(topic);
    }

    public Topic update(Topic updatedTopic) {
        return repo.save(updatedTopic);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Topic> getAllByArticleId(Long articleId) {
        return repo.findAllByArticles_Id(articleId);
    }
}

