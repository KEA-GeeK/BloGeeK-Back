package com.example.BlogPost.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity @Table(name = "Posts")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer post_id;

    @Column(nullable = false)
    private String post_title;

    @Column(nullable = false)
    private String contents;

    private Integer author_id;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime create_at;

    @Column(nullable = false) @UpdateTimestamp
    private LocalDateTime last_modified;

    private Integer category_id;


    public Post(Integer post_id, String post_title, String contents) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.contents = contents;
    }
    public Post(String post_title, String contents) {
        this.post_title = post_title;
        this.contents = contents;
    }
    public Post() {
    }

    public String printModificationDate(){
        if(create_at.equals(last_modified)){
            return "-";
        }
        else if(create_at.toLocalDate().equals(last_modified.toLocalDate())){
                return last_modified.toLocalTime().toString();
        }
        else {
            return last_modified.toLocalDate().toString();
        }
    }

    public String printModificationTime(){
        if(create_at.equals(last_modified)){
            return "-";
        }
        else {
            return last_modified.toLocalDate().toString();
        }
    }
}
