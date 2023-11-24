package Geek.Blog.dto;

import Geek.Blog.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
    private Long blog_id = null;
    private String blog_name;
    private String about_blog;
    private byte[] profilePicture;
    private Long owner_id;

    /** BlogRequestDTO 겸용 **/
    public BlogDTO(Blog blog) {
        blog_id = blog.getBlog_id();
        blog_name = blog.getBlog_name();
        about_blog = blog.getAbout_blog();
        profilePicture = blog.getProfile_picture();
        owner_id = blog.getOwner().getId();
    }
}
