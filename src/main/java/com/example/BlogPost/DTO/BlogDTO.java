package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDTO {
    private Integer blog_id = null;
    private String blog_name;
    private String about_blog;
    private byte[] profilePicture;
    private Integer owner_id;

    public BlogDTO() {
    }
}
