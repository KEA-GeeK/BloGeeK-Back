package Geek.Blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//TODO MSA 형태로 분리
// Member Entity 수정
// 회원가입/회원탈퇴/로그인 기능 덜어내기
// database 확인
// etc

@SpringBootApplication
@EnableJpaAuditing
public class BlogApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);

	}

}
