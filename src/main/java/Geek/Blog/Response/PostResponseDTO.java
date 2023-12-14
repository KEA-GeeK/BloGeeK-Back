package Geek.Blog.Response;

import Geek.Blog.entity.Category;
import Geek.Blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private Long post_id = null;
    private String post_title;
    private String contents;
    private String author;
    private LocalDateTime create_at;
    private LocalDateTime last_modified;
    private Category category;

    public PostResponseDTO(Post post) {
        post_id = post.getPost_id();
        post_title = post.getPost_title();
        contents = post.getContents();
        author = post.getAuthor().getAccount();
        create_at = post.getCreate_at();
        last_modified = post.getLast_modified();
        category = post.getCategory();
    }
}
