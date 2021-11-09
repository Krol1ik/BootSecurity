package com.example.bootsecurity.model;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
//    @NotEmpty(message = "Please fill the message")
    @Size(max = 2048, message = "Message too long")
    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Messages() {
    }


    public String getNameAuthor(){
        return author != null ? author.getUsername() : "<none>";
    }

    public Messages(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
