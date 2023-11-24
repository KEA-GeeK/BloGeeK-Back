package Geek.Blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlogApplication {
	//TODO responseDTO 생성하여 responseBody에 포함될 정보 제한
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);

	}

}
