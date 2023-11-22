package com.example.BlogPost.entity;

import com.example.BlogPost.DTO.PostDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.attoparser.dom.Text;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity @Table(name = "posts")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @Column(nullable = false)
    private Text post_title;

    @Column(nullable = false)
    private Text contents;

    private Long author_id;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime create_at;

    @Column(nullable = false) @UpdateTimestamp
    private LocalDateTime last_modified;

    private Long category_id;


    public Post(Long post_id, Text post_title, Text contents) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.contents = contents;
    }
    public Post(Text post_title, Text contents) {
        this.post_title = post_title;
        this.contents = contents;
    }
    public Post(PostDTO uploadDTO){
        this.post_id = uploadDTO.getPost_id();
        this.post_title = uploadDTO.getPost_title();
        this.contents = uploadDTO.getContents();
        //외래키
//        this.author_id = uploadDTO.getAuthor_id();
//        this.category_id = uploadDTO.getCategory_id();
    }

    public Post() {
    }

    public void importPostData(PostDTO uploadDTO){
        this.post_title = uploadDTO.getPost_title();
        this.contents = uploadDTO.getContents();
        //외래키
//        this.author_id = uploadDTO.getAuthor_id();
//        this.category_id = uploadDTO.getCategory_id();
    }
}
