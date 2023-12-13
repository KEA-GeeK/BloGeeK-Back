package Geek.Blog.Response;

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
    private Long author_id;
    private LocalDateTime create_at;
    private LocalDateTime last_modified;
    private Long category_id;

    public PostResponseDTO(Post post) {
        post_id = post.getPost_id();
        post_title = post.getPost_title();
        contents = post.getContents();
        author_id = post.getAuthor().getId();
        create_at = post.getCreate_at();
        last_modified = post.getLast_modified();
        category_id = post.getCategory_id();
    }
}
