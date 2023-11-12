package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDTO {
    private Integer reply_id = null;
    private String contents;
    private Integer comment_id;
    //외래키
//    private Integer author_id;
//    private Integer category_id;

    public ReplyDTO(Integer reply_id, String contents, Integer comment_id) {
        this.reply_id = reply_id;
        this.contents = contents;
        this.comment_id = comment_id;
        //외래키
//        this.author_id = author_id;
//        this.category_id = category_id;
    }
    public ReplyDTO() {
    }
}
