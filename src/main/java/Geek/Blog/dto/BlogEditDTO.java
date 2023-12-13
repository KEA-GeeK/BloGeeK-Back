package Geek.Blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogEditDTO {
    private Long claimer_id;
    private String blog_name;
    private String about_blog;
    private byte[] profilePicture;
}
