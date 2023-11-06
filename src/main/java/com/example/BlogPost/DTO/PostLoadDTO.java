package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostLoadDTO {
    private Integer post_id;
    private String post_title;
    private String contents;
    private Integer author_id;
    private LocalDateTime create_at;
    private LocalDateTime last_modified;
    private Integer category_id;

    public PostLoadDTO(Integer post_id, String post_title, String contents, Integer author_id, LocalDateTime create_at, LocalDateTime last_modified, Integer category_id) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.contents = contents;
        this.author_id = author_id;
        this.create_at = create_at;
        this.last_modified = last_modified;
        this.category_id = category_id;
    }
    public PostLoadDTO(){}
}
