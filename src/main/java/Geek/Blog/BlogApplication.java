package Geek.Blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//TODO API 링크 재정비 (SecurityConfig참조, 상하구조 확실하게)
//TODO 카테고리 테이블 만들기

@SpringBootApplication
@EnableJpaAuditing
public class BlogApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);

	}

}
