package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;
import org.attoparser.dom.Text;

@Getter
@Setter
public class PostDTO {
    private Long post_id = null;
    private Text post_title;
    private Text contents;
    private Long author_id;
    private Long category_id;
    
    public PostDTO() {
    }
}
