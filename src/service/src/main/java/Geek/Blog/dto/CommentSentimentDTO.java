package Geek.Blog.dto;

import Geek.Blog.entity.Comment;
import Geek.Blog.entity.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentSentimentDTO {
    private Long comment_id = null;
    private String contents;
    private Boolean unsmile;
    private Sentiment sentiment;

    public CommentSentimentDTO(Comment comment){
        comment_id = comment.getComment_id();
        contents = comment.getContents();
        unsmile = comment.getUnsmile();
        sentiment = comment.getSentiment();
    }
}
