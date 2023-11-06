package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEditDTO {
    private Integer post_id;
    private String post_title;
    private String contents;

    public PostEditDTO(Integer post_id, String post_title, String contents) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.contents = contents;
    }
    public PostEditDTO(){}
}
