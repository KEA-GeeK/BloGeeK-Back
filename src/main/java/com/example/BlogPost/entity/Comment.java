package com.example.BlogPost.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity @Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comment_id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    Post post;

    private Integer author_id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime create_at;

    @Column(nullable = false) @UpdateTimestamp
    private LocalDateTime last_modified;

    public Comment() {
    }
}
