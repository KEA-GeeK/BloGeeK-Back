package Geek.Blog.service;

import Geek.Blog.repository.ReplyRepository;
import Geek.Blog.dto.ReplyDTO;
import Geek.Blog.entity.Reply;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    /**
     * 기능
     **/
    public Reply upload(ReplyDTO replyDTO) {
        try {
            Reply reply = replyRepository.upload(replyDTO);
            return reply;
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<Reply> viewReply(Long replyId) {
            return replyRepository.findById(replyId);
    }

    public List<Reply> listReplies() {
        return replyRepository.findAll();
    }

    public void editReply(Reply reply){
        replyRepository.edit(reply);
    }

    public void deleteReply(Reply reply){
        replyRepository.deleteById(reply.getReply_id());
    }
}
