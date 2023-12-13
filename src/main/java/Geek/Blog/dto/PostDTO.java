package Geek.Blog.dto;

import Geek.Blog.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String post_title;
    private String contents;
    private Long claimer_id;
    private Category category;
}
