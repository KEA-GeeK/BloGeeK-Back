package Geek.Blog.Response;

import lombok.Data;

@Data
public class SignUpResponse {
    private String token;

    public SignUpResponse(String token){
        this.token = token;
    }
    public void setToken(String token){
        this.token = token;
    }
}
