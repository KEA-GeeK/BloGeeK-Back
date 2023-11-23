package com.example.BlogPost.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity @Table(name = "likes")
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    Post post;

    private Long member_id;

    public Like() {
    }
}
