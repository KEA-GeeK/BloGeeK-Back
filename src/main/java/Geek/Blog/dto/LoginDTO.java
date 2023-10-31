package Geek.Blog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {
    private String id;
    private String pw;
}
