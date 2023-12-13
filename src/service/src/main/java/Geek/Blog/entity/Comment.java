package Geek.Blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Member author;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime create_at;

    @Column(nullable = false) @UpdateTimestamp
    private LocalDateTime last_modified;

    private Boolean unsmile;

    private Sentiment sentiment;
}
