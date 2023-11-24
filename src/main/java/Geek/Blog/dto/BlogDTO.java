package Geek.Blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDTO {
    private Long blog_id = null;
    private String blog_name;
    private String about_blog;
    private byte[] profilePicture;
    private Long owner_id;

    public BlogDTO() {
    }
}
