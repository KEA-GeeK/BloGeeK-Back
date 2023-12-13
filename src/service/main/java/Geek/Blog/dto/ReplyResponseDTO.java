package Geek.Blog.dto;

import Geek.Blog.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDTO {
    private Long reply_id = null;
    private String contents;
    private Long author_id;
    private Long comment_id;
    private LocalDateTime create_at;
    private LocalDateTime last_modified;

    public ReplyResponseDTO(Reply reply) {
        reply_id = reply.getReply_id();
        contents = reply.getContents();
        author_id = reply.getAuthor().getId();
        comment_id = reply.getComment().getComment_id();
        create_at = reply.getCreate_at();
        last_modified = reply.getLast_modified();
    }
}
