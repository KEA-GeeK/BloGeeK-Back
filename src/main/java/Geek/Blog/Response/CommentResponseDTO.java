package Geek.Blog.Response;

import Geek.Blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long comment_id = null;
    private String contents;
    private Long post_id;
    private Long author_id;
    private LocalDateTime create_at;
    private LocalDateTime last_modified;

    public CommentResponseDTO(Comment comment){
        comment_id = comment.getComment_id();
        post_id = comment.getPost().getPost_id();
        author_id = comment.getAuthor().getId();
        contents = comment.getContents();
        create_at = comment.getCreate_at();
        last_modified = comment.getLast_modified();
    }
}
