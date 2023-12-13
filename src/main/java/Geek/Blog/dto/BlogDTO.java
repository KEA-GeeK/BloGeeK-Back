package Geek.Blog.dto;

import Geek.Blog.entity.Blog;
import Geek.Blog.entity.Member;
import Geek.Blog.util.ImageUtil;
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

    public BlogDTO(Member member){
        blog_name = member.getAccount() + "의 블로그";
        about_blog = "어서오세요 " + member.getAccount() + "의 블로그 입니다.";
        profilePicture = ImageUtil.convertImageToByteArray("src/main/resources/static/images/default-profile.png");
        owner_id = member.getId();
    }
}
