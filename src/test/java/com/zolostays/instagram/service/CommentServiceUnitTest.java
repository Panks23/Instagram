package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.CommentDoesNotExistException;
import com.zolostays.instagram.exception.PostDoesNotExistException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.Comment;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.CommentRepository;
import com.zolostays.instagram.sampleData.commentData.CommentData;
import com.zolostays.instagram.sampleData.postData.PostData;
import com.zolostays.instagram.sampleData.userData.UserData;
import com.zolostays.instagram.util.CommentUtil;
import com.zolostays.instagram.util.PostUtil;
import com.zolostays.instagram.util.UserUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class CommentServiceUnitTest {

    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserUtil userUtil;

    @Mock
    private PostUtil postUtil;

    @Mock
    private CommentUtil commentUtil;

    @Mock
    private ModelMapper modelMapper1;
    UserDTO userDTO;

    PostDTO postDTO;

    CommentDTO commentDTO;

    User user;

    Post post;

    Comment comment;


    @Before
    public void setUp(){
        userDTO = UserData.getNewUserDTO();
        user = modelMapper.map(userDTO, User.class);

        postDTO = PostData.getNewPostDTO();
        modelMapper.typeMap(PostDTO.class, Post.class).addMapping(PostDTO::getUser_DTO, Post::setUser)
                .addMapping(PostDTO::getList_image_DTO, Post::setImageList);
        post = modelMapper.map(postDTO, Post.class);

        commentDTO = CommentData.getCommentData();
        modelMapper.typeMap(CommentDTO.class, Comment.class).addMapping(CommentDTO::getCommentId, Comment::setCommentId)
                .addMapping(CommentDTO::getPost_DTO, Comment::setPost).addMapping(
                        CommentDTO::getUser_DTO, Comment::setUser);
        comment = modelMapper.map(commentDTO, Comment.class);
        comment.setId(1l);
        comment.setUser(user);
        comment.setPost(post);
        try {
            given(userUtil.getUser(userDTO.getId())).willReturn(user);
        }catch (UserDoesNotExistException userDoesNotExistException){
            assertTrue(false);
        }
        try {
            given(postUtil.getPost(postDTO.getId())).willReturn(post);
        }catch (PostDoesNotExistException postDoesNotExistException){
            assertTrue(false);
        }
    }

    @Test
    public void createCommentSuccess(){

        given(modelMapper1.map(commentDTO, Comment.class)).willReturn(comment);
        given(commentRepository.save(comment)).willReturn(comment);
        given(modelMapper1.map(comment, CommentDTO.class)).willReturn(commentDTO);

        try {
            CommentDTO result = commentService.createComment(commentDTO, userDTO.getId(), postDTO.getId());
            assertEquals(commentDTO, result);
        }catch (BaseException baseException){
            assertTrue(false);
        }

    }

    @Test
    public void getAllCommentSuccess(){
        given(commentRepository.findAllByPostAndCommentIdIsNull(post)).willReturn(Arrays.asList(comment));
        Type listType = new TypeToken<List<CommentDTO>>(){}.getType();
        given(modelMapper1.map(Arrays.asList(comment), listType)).willReturn(Arrays.asList(commentDTO));

        try {
            List<CommentDTO> commentDTOList = commentService.getAllComment(userDTO.getId(), postDTO.getId());
            assertEquals(Arrays.asList(commentDTO), commentDTOList);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }

    @Test
    public void updateCommentSuccess(){
        try {
            given(commentUtil.getComment(comment.getId(), user, post)).willReturn(comment);
        }catch (CommentDoesNotExistException commentDoesNotExistException){
            assertTrue(false);
        }
        CommentDTO updatedCommentDTO = CommentData.getUpdatedCommentData();
        Comment updatedComment = modelMapper.map(updatedCommentDTO, Comment.class);
        updatedComment.setPost(post);
        updatedComment.setUser(user);
        updatedComment.setId(comment.getId());
        System.out.println(updatedComment);
        given(commentRepository.save(updatedComment)).willReturn(updatedComment);
        given(modelMapper1.map(updatedComment, CommentDTO.class)).willReturn(updatedCommentDTO);
        try {
            CommentDTO result = commentService.updateComment(updatedCommentDTO, userDTO.getId(), postDTO.getId(),
                    comment.getId());
            assertEquals(updatedCommentDTO, result);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }


    @Test
    public void deleteCommentSuccess(){
        given(commentRepository.existsByIdAndUserAndPostAndCommentIdIsNull(comment.getId(), user, post)).willReturn(true);
        doNothing().when(commentRepository).deleteByIdAndUserAndPostAndCommentIdIsNull(comment.getId(), user, post);

        try {
            commentService.deleteComment(user.getId(), post.getId(), comment.getId());
            assertTrue(true);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }

    @Test
    public void deleteCommentShouldThrowCommentDoesNotExistException(){
        given(commentRepository.existsByIdAndUserAndPostAndCommentIdIsNull(comment.getId(), user, post)).willReturn(false);
        doNothing().when(commentRepository).deleteByIdAndUserAndPostAndCommentIdIsNull(comment.getId(), user, post);

        try {
            commentService.deleteComment(user.getId(), post.getId(), comment.getId());
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }


}
