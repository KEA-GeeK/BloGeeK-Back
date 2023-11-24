package Geek.Blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity @Table(name = "blog")
public class Blog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blog_id;

    @Column(nullable = false)
    private String blog_name;

    private String about_blog;

    @Lob
    private byte[] profile_picture;

    private Long owner_id;

    public Blog() {
    }
}
