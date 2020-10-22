package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.model.Comment;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.CommentRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        modelMapper.typeMap(Comment.class, CommentDTO.class).addMapping(Comment::getPost, CommentDTO::setPostDTO);

    }
    @Override
    public ResponseDTO createComment(CommentDTO commentDTO, Long user_id, Long post_id) {
        return userRepository.findById(user_id).map(user -> {
            return postRepository.findById(post_id).map(post -> {
                Comment comment = modelMapper.map(commentDTO, Comment.class);
                comment.setUser(user);
                comment.setPost(post);
                return Mapper.responseDTOSingle(commentRepository.save(comment), "You have commented");
            }).orElse(Mapper.responseDTOSingle(null, "Post Doesn't exist"));
        }).orElse(Mapper.responseDTOSingle(null, "User doesn't exist"));

    }

    @Override
    public ResponseDTO getAllComment(Long user_id, Long post_id) {
        return userRepository.findById(user_id).map(user -> {
            return postRepository.findById(post_id).map(post -> {
                List<Comment> comments = commentRepository.findAllByPostAndCommentIdIsNull(post);
                Type listType = new TypeToken<List<CommentDTO>>(){}.getType();
                List<CommentDTO> commentDTO = modelMapper.map(comments, listType);
                return Mapper.responseDTO(commentDTO, "All comments of post");
            }).orElse(Mapper.responseDTOSingle(null, "Post doesn't exist"));
        }).orElse(Mapper.responseDTOSingle(null, "User doesn't exist"));
    }

    @Override
    public ResponseDTO updateComment(CommentDTO commentDTO, Long user_id, Long post_id, Long comment_id) {
        try{
            User user = getUser(user_id);
            Post post = getPost(post_id);
            Comment originalComment = getComment(comment_id);
            checkPostAndComment(post, originalComment);
            checkCommentAndUser(originalComment, user);
            checkIfItsCommentAndNotReply(originalComment);
            Comment comment = modelMapper.map(commentDTO, Comment.class);
            originalComment.setComment(comment.getComment());
            return Mapper.responseDTOSingle(commentRepository.save(originalComment), "You have updated your comment");

        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }

    @Override
    public ResponseDTO deleteComment(Long user_id, Long post_id, Long comment_id) {
        try {
            User user = getUser(user_id);
            Post post = getPost(post_id);
            Comment comment = getComment(comment_id);
            checkPostAndComment(post, comment);
            checkCommentAndUser(comment, user);
            commentRepository.deleteById(comment_id);
            return Mapper.objectDeleted("You deleted your comment");
        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }


    @Override
    public ResponseDTO replyToComment(CommentDTO commentDTO, Long comment_id, Long user_id, Long post_id) {
        return userRepository.findById(user_id).map( user -> {
            Comment reply = modelMapper.map(commentDTO, Comment.class);
            reply.setUser(user);
            return postRepository.findById(post_id).map(post -> {
                return commentRepository.findById(comment_id).map(comment -> {
                    try {
                        checkIfItsCommentAndNotReply(comment);
                        reply.setCommentId(comment);
                        reply.setPost(comment.getPost());
                        Comment resultReply  = commentRepository.save(reply);
                        return Mapper.responseDTOSingle(resultReply, "You have replied");
                    } catch (BaseException e) {
                        return Mapper.responseDTOSingle(null, e.getMessage());
                    }
                }).orElse(Mapper.responseDTOSingle(null, "Comment Doesn't exist"));
            }).orElse(Mapper.responseDTOSingle(null, "Post doesn't exist"));

        }).orElse(Mapper.responseDTOSingle(null, "User doesn't exist"));
    }



    public ResponseDTO updateReplyToComment(CommentDTO commentDTO, Long comment_id,
                                                     Long user_id, Long reply_id, Long post_id){
        try{
            validateForReply(user_id, post_id, comment_id, reply_id);
            Comment newReply = modelMapper.map(commentDTO, Comment.class);
            Comment reply = getReply(reply_id);
            return Mapper.responseDTOSingle(updateReply(reply, newReply), "You have replied");
        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }

    public ResponseDTO deleteReplyToComment(Long user_id, Long post_id, Long comment_id, Long reply_id){

        try{
            validateForReply(user_id, post_id, comment_id, reply_id);
            commentRepository.deleteById(reply_id);
            return Mapper.responseDTOSingle(new ArrayList<>(), "Reply Deleted");
        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }

    }

    @Override
    public ResponseDTO getCommentAndReplies(Long user_id, Long post_id, Long comment_id) {
        try{
            User user = getUser(user_id);
            Post post = getPost(post_id);
            Comment comment = getComment(comment_id);
            checkPostAndComment(post, comment);
            checkIfItsCommentAndNotReply(comment);
            List<Comment> replies = commentRepository.findAllByCommentId(comment);
            return Mapper.responseDTO(replies, "You have recieved for all the replies");
        }catch (BaseException e){
            return Mapper.responseDTOSingle(null, e.getMessage());
        }

    }

    public void validateForReply(Long user_id, Long post_id, Long comment_id, Long reply_id) throws BaseException{
        User user = getUser(user_id);
        Post post = getPost(post_id);
        Comment comment = getComment(comment_id);
        Comment reply = getReply(reply_id);
        isReplyToTheSameComment(reply, comment);
        checkUserAllowedToUpdateReply(reply, user);
        checkPostAndReply(reply, post);
    }


    public User getUser(Long user_id) throws BaseException {
        Optional<User> optionalUser = userRepository.findById(user_id);
     if(optionalUser.isPresent()){
         return optionalUser.get();
     }
     throw new BaseException("User doesn't Exist");
    }

    public Post getPost(Long post_id) throws BaseException{
        Optional<Post> post = postRepository.findById(post_id);
        if (post.isPresent()){
            return post.get();
        }
        throw new BaseException("Post is not Present");
    }

    public Comment getComment(Long comment_id) throws BaseException{
        Optional<Comment> optionalComment = commentRepository.findById(comment_id);
        if (optionalComment.isPresent()){
            return optionalComment.get();
        }
        throw new BaseException("Comment is not Present");
    }

    public Comment getReply(Long reply_id) throws BaseException{
        Optional<Comment> optionalReply = commentRepository.findById(reply_id);
        if (optionalReply.isPresent()){
            if(optionalReply.get().getCommentId()==null){
                throw new BaseException("Reply id is not a comment");
            }
            return optionalReply.get();
        }
        throw new BaseException("Reply is not Present");
    }

    public boolean isReplyToTheSameComment(Comment reply, Comment comment) throws BaseException{
        if(reply.getCommentId().getId().equals(comment.getId())){
            return true;
        }
        throw new BaseException("Comment id doesn't not match with the fk of comment of reply");
    }

    public void checkUserAllowedToUpdateReply(Comment reply, User user)throws BaseException{
        if(!reply.getUser().equals(user)){
            throw new BaseException("User is not allowed to update Reply");
        }
    }

    public void checkPostAndReply(Comment reply, Post post) throws BaseException{
        if(!reply.getPost().equals(post)){
            throw new BaseException("Reply is not related to the same post");
        }
    }

    public Comment updateReply(Comment reply, Comment newReply){
        reply.setComment(newReply.getComment());
        return commentRepository.save(reply);
    }

    public void checkPostAndComment(Post post, Comment comment) throws BaseException{
        if(!post.getId().equals(comment.getPost().getId())){
            throw new BaseException("Post and comment are not related");
        }
    }

    public void checkIfItsCommentAndNotReply(Comment comment) throws BaseException{
        if(comment.getComment()!=null){
            throw new BaseException("Comment is Reply, Pass valid comment id");
        }
    }

    public void checkCommentAndUser(Comment comment, User user) throws BaseException{
        if(!comment.getUser().getId().equals(user.getId())){
            throw new BaseException("Comment and User are not related");
        }
    }



}
