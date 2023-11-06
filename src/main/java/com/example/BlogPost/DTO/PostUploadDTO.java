package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUploadDTO {
    private Integer post_id = null;
    private String post_title;
    private String contents;
    //외래키
//    private Integer author_id;
//    private Integer category_id;

    public PostUploadDTO(Integer post_id, String post_title, String contents) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.contents = contents;
        //외래키
//        this.author_id = author_id;
//        this.category_id = category_id;
    }
    public PostUploadDTO() {
    }
}
