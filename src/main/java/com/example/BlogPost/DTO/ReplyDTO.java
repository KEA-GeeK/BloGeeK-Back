package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;
import org.attoparser.dom.Text;

@Getter
@Setter
public class ReplyDTO {
    private Long reply_id = null;
    private Text contents;
    private Long author_id;
    private Long comment_id;
    public ReplyDTO() {
    }
}
