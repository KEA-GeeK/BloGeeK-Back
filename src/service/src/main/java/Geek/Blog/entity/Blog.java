package Geek.Blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "blog")
public class Blog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blog_id;

    @Column(nullable = false)
    private String blog_name;

    private String about_blog;

    @Lob
    private byte[] profile_picture;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Member owner;
}
