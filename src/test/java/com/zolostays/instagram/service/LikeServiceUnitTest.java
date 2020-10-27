package com.zolostays.instagram.service;


import com.zolostays.instagram.dto.LikeDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.model.Like;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.LikeRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.sampleData.likeData.LikeData;
import com.zolostays.instagram.sampleData.postData.PostData;
import com.zolostays.instagram.sampleData.userData.UserData;
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
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class LikeServiceUnitTest {

    @InjectMocks
    private LikeServiceImpl likeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private LikeRepository likeRepository;

    ModelMapper modelMapper = new ModelMapper();

    UserDTO userDTO;

    PostDTO postDTO;

    LikeDTO likeDTO;

    User user;

    Post post;

    Like like;

    @Before
    public void setUp(){
        modelMapper.typeMap(PostDTO.class, Post.class).addMapping(PostDTO::getUser_DTO, Post::setUser)
                .addMapping(PostDTO::getList_image_DTO, Post::setImageList);

        modelMapper.typeMap(LikeDTO.class, Like.class).addMapping(LikeDTO::getPost_DTO, Like::setPostId)
                .addMapping(LikeDTO::getUser_DTO, Like::setUser);


        userDTO = UserData.getNewUserDTO();
        postDTO = PostData.getNewPostDTO();
        likeDTO = LikeData.getLikeData();

        user = modelMapper.map(userDTO, User.class);
        post = modelMapper.map(postDTO, Post.class);
        like = modelMapper.map(likeDTO, Like.class);
    }

    @Test
    public void likePost(){

        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.of(post));
        given(likeRepository.existsByUserAndPostId(user, post)).willReturn(false);
        given(likeRepository.save(like)).willReturn(like);

        try{
            Optional<LikeDTO> result = likeService.likePost(userDTO.getId(), postDTO.getId());
            assertEquals(Optional.of(likeDTO), result);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }

    @Test
    public void likePostShouldThrowUserDoesNotExistException(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.empty());
        try{
            Optional<LikeDTO> result = likeService.likePost(userDTO.getId(), postDTO.getId());
            assertEquals(Optional.of(likeDTO), result);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void likePostShouldThrowPostDoesNotExistException(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.empty());

        try{
            Optional<LikeDTO> result = likeService.likePost(userDTO.getId(), postDTO.getId());
            assertEquals(Optional.of(likeDTO), result);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void likePostShouldReturnEmptyIfItAlreadyLikedThePost(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.of(post));
        given(likeRepository.existsByUserAndPostId(user, post)).willReturn(true);
        try{
            Optional<LikeDTO> result = likeService.likePost(userDTO.getId(), postDTO.getId());
            assertEquals(Optional.empty(), result);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }


    @Test
    public void getAllLikeSuccess(){

        List<LikeDTO> listOfLike = Arrays.asList(likeDTO, likeDTO);
        Type listType = new TypeToken<List<Like>>(){}.getType();
        List<Like> likeList = modelMapper.map(listOfLike, listType);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.of(post));
        given(likeRepository.findAllByPostId(post)).willReturn(likeList);

        try{
            List<LikeDTO> result = likeService.getAllLike(userDTO.getId(), postDTO.getId());
            assertEquals(listOfLike, result);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }

    @Test
    public void getAllLikeShouldThrowUserDoesNotExistException(){
        List<LikeDTO> listOfLike = Arrays.asList(likeDTO, likeDTO);
        Type listType = new TypeToken<List<Like>>(){}.getType();
        List<Like> likeList = modelMapper.map(listOfLike, listType);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.empty());

        try{
            List<LikeDTO> result = likeService.getAllLike(userDTO.getId(), postDTO.getId());
            assertEquals(listOfLike, result);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void getAllLikeShouldThrowPostDoesNotExistException(){

        List<LikeDTO> listOfLike = Arrays.asList(likeDTO, likeDTO);
        Type listType = new TypeToken<List<Like>>(){}.getType();
        List<Like> likeList = modelMapper.map(listOfLike, listType);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.empty());
        given(likeRepository.findAllByPostId(post)).willReturn(likeList);

        try{
            List<LikeDTO> result = likeService.getAllLike(userDTO.getId(), postDTO.getId());
            assertEquals(listOfLike, result);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void dislikePostSuccessfully(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.of(post));
        given(likeRepository.existsByUserAndPostId(user, post)).willReturn(true);
        doNothing().when(likeRepository).deleteByPostIdAndUser(post, user);

        try {
            boolean isDeleted = likeService.dislikePost(userDTO.getId(), postDTO.getId());
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }

    @Test
    public void dislikePostShouldThrowUserDoesNotExistException(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.empty());
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.of(post));
        given(likeRepository.existsByUserAndPostId(user, post)).willReturn(true);
        doNothing().when(likeRepository).deleteByPostIdAndUser(post, user);

        try {
            boolean isDeleted = likeService.dislikePost(userDTO.getId(), postDTO.getId());
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void dislikePostShouldThrowPostDoesNotExist(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.empty());
        given(likeRepository.existsByUserAndPostId(user, post)).willReturn(true);
        doNothing().when(likeRepository).deleteByPostIdAndUser(post, user);

        try {
            boolean isDeleted = likeService.dislikePost(userDTO.getId(), postDTO.getId());
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void dislikePostShouldFailAsUserNeverLikedPost(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findById(postDTO.getId())).willReturn(Optional.of(post));
        given(likeRepository.existsByUserAndPostId(user, post)).willReturn(false);
        doNothing().when(likeRepository).deleteByPostIdAndUser(post, user);

        try {
            boolean isDeleted = likeService.dislikePost(userDTO.getId(), postDTO.getId());
            assertFalse(isDeleted);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }

}
