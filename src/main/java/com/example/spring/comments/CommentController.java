package com.example.spring.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CommentController {
    private CommentService commentService;

    public CommentController(@Autowired CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public List<Comment> getAll(@RequestParam(required = false) Long articleId ) {
        if (articleId != null) {
            return commentService.getAllByArticleId(articleId);
        } else {
            return commentService.getAll();
        }
    }

    @GetMapping("comments/{id}")
    public Comment getById(@PathVariable Long id) {
        return commentService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/comments")
    public Comment create(@RequestBody Comment comment) {
        return commentService.create(comment);
    }

    @PutMapping("/comments")
    public Comment update(@RequestBody Comment updatedComment) {
        return commentService.update(updatedComment);
    }

    @DeleteMapping("comments/{id}")
    public void delete(@PathVariable Long id) {
        commentService.delete(id);
    }

}
