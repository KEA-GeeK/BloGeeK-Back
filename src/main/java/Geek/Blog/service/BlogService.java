package Geek.Blog.service;

import Geek.Blog.dto.BlogDTO;
import Geek.Blog.entity.Blog;
import Geek.Blog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

//TODO DB에서 받아온 데이터가 유효한지 try catch
@Transactional
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Optional<Blog> viewBlog(Long blogId) {
            return blogRepository.findById(blogId);
    }

    public void editBlog(Blog blog){
        blogRepository.edit(blog);
    }

    /** 회원 가입,탈퇴시 사용 (BlogController에서 사용 X)**/
    //TODO 회원 가입·회원 탈퇴 과정에 끼워넣기
    public Blog create(BlogDTO blogDTO) {
        Blog blog = blogRepository.create(blogDTO);
        return blog;
    }
    public void deleteBlog(Blog blog){
        blogRepository.deleteById(blog.getBlog_id());
    }
}
