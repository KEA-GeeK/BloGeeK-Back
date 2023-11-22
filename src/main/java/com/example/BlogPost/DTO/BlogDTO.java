package com.example.BlogPost.DTO;

import lombok.Getter;
import lombok.Setter;
import org.attoparser.dom.Text;

@Getter
@Setter
public class BlogDTO {
    private Long blog_id = null;
    private Text blog_name;
    private Text about_blog;
    private byte[] profilePicture;
    private Long owner_id;

    public BlogDTO() {
    }
}
