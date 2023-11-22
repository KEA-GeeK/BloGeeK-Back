package com.example.BlogPost.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.attoparser.dom.Text;

@Getter
@Setter
@Entity @Table(name = "blog")
public class Blog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blog_id;

    @Column(nullable = false)
    private Text blog_name;

    private Text about_blog;

    @Lob
    private byte[] profilePicture;

    private Long owner_id;

    public Blog() {
    }
}
