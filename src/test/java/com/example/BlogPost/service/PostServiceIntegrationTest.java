package com.example.BlogPost.service;

import com.example.BlogPost.entity.Post;
import com.example.BlogPost.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceIntegrationTest {

    @Autowired PostService postService;
    @Autowired PostRepository postRepository;

    @Test
    void 업로드() {
//        //given
//        PostUploadDTO post = new PostUploadDTO();
//        post.setPost_title("테스트용 제목입니다.");
//        post.setContents("테스트용 본문입니다.");
//        //when
//        Integer postedId = postService.upload(post);
//        //then
//        Post findPost = postService.viewPost(postedId).get();
//        AssertPost(findPost, post);
    }

    @Test
    void 열람() {
//        //given
//        Post post1 = new Post();
//        post1.setPost_title("첫번째 테스트용 제목입니다.");
//        post1.setContents("첫번째 테스트용 본문입니다.");
//
//        Post post2 = new Post();
//        post2.setPost_title("두번째 테스트용 제목입니다.");
//        post2.setContents("두번째 테스트용 본문입니다.");
//        //when
//        Integer postedIdFirst = postService.upload(post1);
//        Integer postedIdSecond = postService.upload(post2);
//        //then
//        Post findFirstPost = postService.viewPost(postedIdFirst).get();
//        Post findSecondPost = postService.viewPost(postedIdSecond).get();
//
//        PrintPost(findFirstPost);
//        PrintPost(findSecondPost);
    }

    @Test
    void 수정(){
//        Post post = new Post();
//        post.setPost_title("테스트용 제목입니다.");
//        post.setContents("테스트용 본문입니다.");
//
//        Post newPost = new Post("수정된 테스트용 제목입니다.", "수정된 테스트용 본문입니다.");
//
//        Integer postedId = postService.upload(post);
//        Post uplodedPost = postService.viewPost(postedId).get();
//
//        Post editerPost = postService.editPost(newPost);
//        Post editedPost = postService.viewPost(postedId).get();
//        AssertPost(uplodedPost, editedPost);
    }

    @Test
    void 삭제(){
//        Post post = new Post();
//        post.setPost_title("삭제 테스트 제목");
//        post.setContents("삭제 테스트 본문");
//
//        Integer postedId = postService.upload(post);
//
//        Integer deleted = postService.deletePost(post);
//        Assertions.assertThat(deleted)
//                .isEqualTo(1);
    }



    private static void AssertPost(Post findPost, Post post) {
        assertThat(findPost.getPost_id()).isEqualTo(post.getPost_id());
        assertThat(findPost.getPost_title()).isEqualTo(post.getPost_title());
        assertThat(findPost.getContents()).isEqualTo(post.getContents());
        System.out.println(findPost.getCreate_at());
        System.out.println(findPost.getLast_modified());
    }

    private static void PrintPost(Post post) {
        System.out.println(post.getPost_id());
        System.out.println(post.getPost_title());
        System.out.println(post.getContents());
        System.out.println(post.getAuthor_id());
        System.out.println(post.getCreate_at().toString());
        System.out.println(post.getLast_modified().toString());
        System.out.println("----------------------------");
    }
}