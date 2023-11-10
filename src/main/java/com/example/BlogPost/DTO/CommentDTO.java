package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Integer comment_id = null;
    private String contents;
    //외래키
//    private Integer author_id;
//    private Integer category_id;

    public CommentDTO(Integer comment_id, String contents) {
        this.comment_id = comment_id;
        this.contents = contents;
        //외래키
//        this.author_id = author_id;
//        this.category_id = category_id;
    }
    public CommentDTO() {
    }
}
