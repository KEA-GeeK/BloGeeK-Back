package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDTO {
    private Integer reply_id = null;
    private String contents;
    private Integer author_id;
    private Integer comment_id;
    public ReplyDTO() {
    }
}
