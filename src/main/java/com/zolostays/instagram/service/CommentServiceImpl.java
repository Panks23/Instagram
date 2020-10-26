package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.exception.*;
import com.zolostays.instagram.model.Comment;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.CommentRepository;
import com.zolostays.instagram.util.CommentUtil;
import com.zolostays.instagram.util.PostUtil;
import com.zolostays.instagram.util.UserUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;


@Component
public class CommentServiceImpl  implements ICommentService{


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private PostUtil postUtil;

    @Autowired
    private CommentUtil commentUtil;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Long user_id, Long post_id) throws BaseException{
        User user = userUtil.getUser(user_id);
        Post post = postUtil.getPost(post_id);
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setUser(user);
        comment.setPost(post);
        return modelMapper.map(commentRepository.save(comment), CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getAllComment(Long user_id, Long post_id) throws BaseException {
        User user = userUtil.getUser(user_id);
        Post post = postUtil.getPost(post_id);
        List<Comment> comments = commentRepository.findAllByPostAndCommentIdIsNull(post);
        Type listType = new TypeToken<List<CommentDTO>>(){}.getType();
        List<CommentDTO> commentDTO = modelMapper.map(comments, listType);
        return commentDTO;
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Long user_id, Long post_id, Long comment_id) throws BaseException {
        User user = userUtil.getUser(user_id);
        Post post = postUtil.getPost(post_id);
        Comment originalComment = commentUtil.getComment(comment_id, user, post);
        originalComment.setComment(commentDTO.getComment());
        return modelMapper.map(commentRepository.save(originalComment), CommentDTO.class);
    }

    @Override
    @Transactional
    public void deleteComment(Long user_id, Long post_id, Long comment_id) throws BaseException{
        User user = userUtil.getUser(user_id);
        Post post = postUtil.getPost(post_id);

        if(!commentRepository.existsByIdAndUserAndPostAndCommentIdIsNull(comment_id, user, post)){
            throw new CommentDoesNotExistException(comment_id.toString());
        }
        commentRepository.deleteByIdAndUserAndPostAndCommentIdIsNull(comment_id, user, post);


    }

    //Ask

    @Override
    public CommentDTO reply(CommentDTO commentDTO, Long comment_id, Long user_id, Long post_id) throws BaseException{

        User user = userUtil.getUser(user_id);
        Post post = postUtil.getPost(post_id);

        Optional<Comment> commentOptional = commentRepository.findByIdAndPostAndCommentIdIsNull(comment_id,
                post);
        if(commentOptional.isEmpty()){
            throw new CommentDoesNotExistException(comment_id.toString());
        }
        Comment reply = modelMapper.map(commentDTO, Comment.class);
        reply.setPost(post);
        reply.setUser(user);
        reply.setCommentId(commentOptional.get());
        return modelMapper.map(commentRepository.save(reply), CommentDTO.class);
    }



    public CommentDTO updateReply(CommentDTO commentDTO, Long comment_id,
                                                     Long user_id, Long reply_id, Long post_id) throws BaseException{
        User user = userUtil.getUser(user_id);
        Post post = postUtil.getPost(post_id);

        Optional<Comment> commentOptional = commentRepository.findByIdAndPostAndCommentIdIsNull(comment_id,
                post);
        if(commentOptional.isEmpty()){
            throw new CommentDoesNotExistException(comment_id.toString());
        }

        Optional<Comment> originalReplyOptional = commentRepository.findByIdAndPostAndUserAndCommentId(reply_id, post,
                user, commentOptional.get());

        if(originalReplyOptional.isEmpty()){
            throw new ReplyDoesNotExistException(reply_id.toString());
        }

        originalReplyOptional.get().setComment(commentDTO.getComment());
        return modelMapper.map(commentRepository.save(originalReplyOptional.get()), CommentDTO.class);
    }

    @Override
    @Transactional
    public void deleteReply(Long user_id, Long post_id, Long comment_id, Long reply_id) throws BaseException{

        User user = userUtil.getUser(user_id);
        Post post = postUtil.getPost(post_id);

        Optional<Comment> commentOptional = commentRepository.findByIdAndPostAndCommentIdIsNull(comment_id,
                post);
        if(commentOptional.isEmpty()){
            throw new CommentDoesNotExistException(comment_id.toString());
        }
        if(!commentRepository.existsByIdAndUserAndPostAndCommentId(reply_id, user
                , post, commentOptional.get())){
            throw new ReplyDoesNotExistException(reply_id.toString());
        }
        commentRepository.deleteByIdAndUserAndPostAndCommentId(reply_id, user
                                    , post, commentOptional.get());
    }

    @Override
    public List<CommentDTO> getCommentAndReplies(Long user_id, Long post_id, Long comment_id) throws BaseException {

        User user = userUtil.getUser(user_id);
        Post post = postUtil.getPost(post_id);

        Optional<Comment> commentOptional = commentRepository.findByIdAndPostAndCommentIdIsNull(comment_id,
                post);

        if(commentOptional.isEmpty()){
            throw new CommentDoesNotExistException(comment_id.toString());
        }
        Type listType = new TypeToken<List<CommentDTO>>(){}.getType();
        return modelMapper.map(commentRepository.findAllByCommentId(commentOptional.get()), listType);
    }

//    public void validateForReply(Long user_id, Long post_id, Long comment_id, Long reply_id) throws BaseException{
//        User user = getUser(user_id);
//        Post post = getPost(post_id);
//        Comment comment = getComment(comment_id);
//        Comment reply = getReply(reply_id);
//        isReplyToTheSameComment(reply, comment);
//        checkUserAllowedToUpdateReply(reply, user);
//        checkPostAndReply(reply, post);
//    }


//    public User getUser(Long user_id) throws BaseException {
//        Optional<User> optionalUser = userRepository.findById(user_id);
//     if(optionalUser.isPresent()){
//         return optionalUser.get();
//     }
//     throw new BaseException("User doesn't Exist");
//    }

//    public Post getPost(Long post_id) throws BaseException{
//        Optional<Post> post = postRepository.findById(post_id);
//        if (post.isPresent()){
//            return post.get();
//        }
//        throw new BaseException("Post is not Present");
//    }

//    public Comment getComment(Long comment_id) throws BaseException{
//        Optional<Comment> optionalComment = commentRepository.findById(comment_id);
//        if (optionalComment.isPresent()){
//            return optionalComment.get();
//        }
//        throw new BaseException("Comment is not Present");
//    }

//    public Comment getReply(Long reply_id) throws BaseException{
//        Optional<Comment> optionalReply = commentRepository.findById(reply_id);
//        if (optionalReply.isPresent()){
//            if(optionalReply.get().getCommentId()==null){
//                throw new BaseException("Reply id is not a comment");
//            }
//            return optionalReply.get();
//        }
//        throw new BaseException("Reply is not Present");
//    }
//
//    public boolean isReplyToTheSameComment(Comment reply, Comment comment) throws BaseException{
//        if(reply.getCommentId().getId().equals(comment.getId())){
//            return true;
//        }
//        throw new BaseException("Comment id doesn't not match with the fk of comment of reply");
//    }

//    public void checkUserAllowedToUpdateReply(Comment reply, User user)throws BaseException{
//        if(!reply.getUser().equals(user)){
//            throw new BaseException("User is not allowed to update Reply");
//        }
//    }

//    public void checkPostAndReply(Comment reply, Post post) throws BaseException{
//        if(!reply.getPost().equals(post)){
//            throw new BaseException("Reply is not related to the same post");
//        }
//    }
//
//    public Comment updateReply(Comment reply, Comment newReply){
//        reply.setComment(newReply.getComment());
//        return commentRepository.save(reply);
//    }

//    public void checkPostAndComment(Post post, Comment comment) throws BaseException{
//        if(!post.getId().equals(comment.getPost().getId())){
//            throw new BaseException("Post and comment are not related");
//        }
//    }
//
//    public void checkIfItsCommentAndNotReply(Comment comment) throws BaseException{
//        if(comment.getComment()!=null){
//            throw new BaseException("Comment is Reply, Pass valid comment id");
//        }
//    }
//
//    public void checkCommentAndUser(Comment comment, User user) throws BaseException{
//        if(!comment.getUser().getId().equals(user.getId())){
//            throw new BaseException("Comment and User are not related");
//        }
//    }



}
