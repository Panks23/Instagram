package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.model.Comment;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.CommentRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CommentServiceImpl  implements ICommentService{

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    ModelMapper modelMapper = new ModelMapper();

    public CommentServiceImpl(PostRepository postRepository,
            UserRepository userRepository,
            CommentRepository commentRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;

    }
    @Override
    public ResponseDTO createComment(CommentDTO commentDTO, Long user_id, Long post_id) {
        User user = userRepository.findById(user_id).get();
        Post post = postRepository.findById(post_id).get();
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setUser(user);
        comment.setPost(post);
        return Mapper.responseDTOSingle(commentRepository.save(comment), "You have commented");

    }

    @Override
    public ResponseDTO getAllComment(Long post_id) {
        Post post = postRepository.findById(post_id).get();
        List<Comment> comments = commentRepository.findAllByPost(post);
        return Mapper.responseDTO(comments, "All comments of your post");
    }

    @Override
    public ResponseDTO replyToComment(CommentDTO commentDTO, Long comment_id, Long user_id) {
        Comment reply = modelMapper.map(commentDTO, Comment.class);
        User user = userRepository.findById(user_id).get();
        reply.setUser(user);
        Comment comment = commentRepository.findById(comment_id).get();
        reply.setPost(comment.getPost());
        reply.setCommentId(comment);
        Comment resultReply = commentRepository.save(reply);
        return Mapper.responseDTOSingle(resultReply, "You have replied");
    }


    //TODO verify User, Comment and Post
    @Override
    public ResponseDTO updateReplyToComment(CommentDTO commentDTO, Long comment_id, Long user_id, Long reply_id) {
        Comment reply = modelMapper.map(commentDTO, Comment.class);
        Comment originalReply = commentRepository.findById(reply_id).get();
        originalReply.setComment(reply.getComment());
        originalReply = commentRepository.save(originalReply);
        return Mapper.responseDTOSingle(originalReply,"You have updated your reply");
    }

    //TODO verify User, Comment and Post
    @Override
    public ResponseDTO deleteReplyToComment(Long reply_id, Long comment_id, Long user_id) {
        commentRepository.deleteById(reply_id);
        return Mapper.objectDeleted("You deleted Reply");
    }

    @Override
    public ResponseDTO updateComment(CommentDTO commentDTO, Long comment_id) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Comment originaComment = commentRepository.findById(comment_id).get();
        //TODO verify if the same user updating
        String caption  = comment.getComment();
        originaComment.setComment(caption);
        return Mapper.responseDTOSingle(commentRepository.save(originaComment), "You have updated your comment");
    }

    @Override
    public ResponseDTO deleteComment(Long comment_id) {
        //TODO verify if the same user deleting
        commentRepository.deleteById(comment_id);
        return Mapper.objectDeleted("You deleted your comment");
    }


    @Override
    public ResponseDTO getCommentAndReplies(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).get();
        List<Comment> replies = commentRepository.findAllByCommentId(comment);
        return Mapper.responseDTO(replies, "You have recieved for all the replies");
    }
}
