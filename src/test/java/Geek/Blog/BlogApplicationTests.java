//package Geek.Blog;
//
//import Geek.Blog.Post.Post;
//import Geek.Blog.Post.PostService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class BlogApplicationTests {
//	@Autowired
//	private PostService postService;
//
//	@Test
//	public void save() {
//		Post.RequestDto requestDto = new Post.RequestDto();
//		requestDto.setTitle("제목");
//		requestDto.setContents("내용");
//		requestDto.setUseYn("Y");
//		requestDto.setRegisterId(117L);
//		requestDto.setModifyId(117L);
//
//		// 2개의 데이터 입력
//		Assertions.assertNotEquals(0, postService.save(requestDto));
//		Assertions.assertNotEquals(0, postService.save(requestDto));
//	}
//
//}
