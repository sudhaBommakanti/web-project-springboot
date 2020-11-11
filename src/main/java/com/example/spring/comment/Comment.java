package com.example.spring.comment;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    private Long id;
    private String body;
    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
