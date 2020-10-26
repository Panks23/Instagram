package com.zolostays.instagram.service;


import com.zolostays.instagram.dto.ImageDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.Image;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.ImageRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.sampleData.postData.PostData;
import com.zolostays.instagram.sampleData.userData.UserData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class PostServiceUnitTest {

    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    UserRepository userRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    ImageRepository imageRepository;

    @Before
    public void setUp(){
        modelMapper.typeMap(PostDTO.class, Post.class).addMapping(PostDTO::getUser_DTO, Post::setUser)
                .addMapping(PostDTO::getList_image_DTO, Post::setImageList);
    }

    @Test
    public void createPostSuccess(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO  = postDTO.getUser_DTO();
        List<ImageDTO> imageDTOList  = postDTO.getList_image_DTO();

        User user = modelMapper.map(userDTO, User.class);
        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());
        Post post = modelMapper.map(postDTO, Post.class);
        given(postRepository.save(post)).willReturn(post);
        Type listType = new TypeToken<List<Image>>(){}.getType();
        List<Image> listOfImage = modelMapper.map(imageDTOList, listType);
        listOfImage = listOfImage.stream().map(image -> {
            image.setPost(post);
            return image;
        }).collect(Collectors.toList());
        doReturn(listOfImage).when(imageRepository).saveAll(listOfImage);
        try{
            PostDTO postDTOResult = postService.createPost(postDTO, userDTO.getId());
            assertEquals(postDTO, postDTOResult);
        }catch (UserDoesNotExistException e){
            assertFalse(true);
        }
    }

    @Test
    public void createPostFailed(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO  = postDTO.getUser_DTO();

        doReturn(Optional.empty()).when(userRepository).findById(userDTO.getId());
        try{
            PostDTO postDTOResult = postService.createPost(postDTO, userDTO.getId());
            assertFalse(true);
        }catch (UserDoesNotExistException e){
            assertTrue(true);
        }
    }


    @Test
    public void getPostSuccess(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO  = postDTO.getUser_DTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);
        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());
        doReturn(Optional.of(post)).when(postRepository).findByIdAndUser(postDTO.getId(), user);

        try {
            Optional<PostDTO> resultPostDTOOptional = postService.getPost(userDTO.getId(), postDTO.getId());
            assertEquals(Optional.of(postDTO), resultPostDTOOptional);
        }catch (BaseException e){
            assertFalse(true);
        }

    }

    @Test
    public void getPosShouldThrowUserDoesNotExistException(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO  = postDTO.getUser_DTO();

        doReturn(Optional.empty()).when(userRepository).findById(userDTO.getId());
        try {
            Optional<PostDTO> resultPostDTOOptional = postService.getPost(userDTO.getId(), postDTO.getId());
            assertEquals(Optional.of(postDTO), resultPostDTOOptional);
        }catch (BaseException e){
           assertTrue(e.getMessage().contains("User doesn't exist of given id"));
        }
    }

    @Test
    public void getPostShouldThrowPostDoesNotExistException(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();

        User user = modelMapper.map(userDTO, User.class);

        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());
        doReturn(Optional.empty()).when(postRepository).findByIdAndUser(postDTO.getId(), user);

        try {
            Optional<PostDTO> resultPostDTOOptional = postService.getPost(userDTO.getId(), postDTO.getId());
            assertEquals(Optional.of(postDTO), resultPostDTOOptional);
        }catch (BaseException e){
            assertTrue(e.getMessage().contains("Post doesn't exist with given id:"));
        }
    }

    @Test
    public void getAllPostOfUserSuccess(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);

        doReturn(true).when(userRepository).existsById(userDTO.getId());
        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());
        doReturn(Arrays.asList(post)).when(postRepository).findAllByUser(user);

        try{
            List<PostDTO> postDTOList = postService.getAllPost(userDTO.getId());
            assertEquals(Arrays.asList(postDTO), postDTOList);
        }catch (UserDoesNotExistException userDoesNotExistException){
            assertTrue(false);
        }

    }

    @Test
    public void getAllPostOfUserShouldThrowUserDoesNotExistException(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);

        doReturn(false).when(userRepository).existsById(userDTO.getId());
        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());
        doReturn(Arrays.asList(post)).when(postRepository).findAllByUser(user);

        try{
            List<PostDTO> postDTOList = postService.getAllPost(userDTO.getId());
            assertEquals(Arrays.asList(postDTO), postDTOList);
        }catch (UserDoesNotExistException userDoesNotExistException){
            assertTrue(true);
        }
    }

    @Test
   public void deletePostSuccess(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);

        doReturn(true).when(userRepository).existsById(userDTO.getId());
        doReturn(true).when(postRepository).existsByIdAndUser(postDTO.getId(), user);
        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());
        doNothing().when(postRepository).deleteByIdAndUser(post.getId(), user);

        try{
            postService.deletePost(userDTO.getId(), postDTO.getId());
            assertTrue(true);
        }catch (BaseException baseException){
            assertFalse(true);
        }
    }

    @Test
    public void deletePostShouldThrowUserDoesNotExistException(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);

        doReturn(false).when(userRepository).existsById(userDTO.getId());
        try{
            postService.deletePost(userDTO.getId(), postDTO.getId());
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void deletePostShouldThrowPostDoesNotExist(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);

        doReturn(true).when(userRepository).existsById(userDTO.getId());
        doReturn(false).when(postRepository).existsByIdAndUser(postDTO.getId(), user);
        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());

        try{
            postService.deletePost(userDTO.getId(), postDTO.getId());
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void updatePostSuccess(){
        PostDTO postDTO = PostData.getNewPostDTO();
        PostDTO postUpdatedDTO = PostData.getUpdatedPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);
        Post updatedPost = modelMapper.map(postUpdatedDTO ,Post.class);

        doReturn(true).when(userRepository).existsById(userDTO.getId());
        doReturn(true).when(postRepository).existsByIdAndUser(postDTO.getId(), user);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(postRepository.findByIdAndUser(postDTO.getId(), user)).willReturn(Optional.of(post));
        given(postRepository.save(updatedPost)).willReturn(updatedPost);

        try {
           PostDTO result =  postService.updatePost(userDTO.getId(), postDTO.getId(), postUpdatedDTO);
            assertEquals(postUpdatedDTO, result);
        }catch (BaseException baseException){
            assertTrue(false);
        }

    }


    @Test
    public void updatePostShouldThrowUserDoesNotException(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();
        PostDTO postUpdatedDTO = PostData.getUpdatedPostDTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);

        doReturn(false).when(userRepository).existsById(userDTO.getId());
        try{
            PostDTO result =  postService.updatePost(userDTO.getId(), postDTO.getId(), postUpdatedDTO);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void updatePostShouldThrowPostDoesNotExistException(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO = UserData.getNewUserDTO();
        PostDTO postUpdatedDTO = PostData.getUpdatedPostDTO();

        User user = modelMapper.map(userDTO, User.class);
        Post post = modelMapper.map(postDTO, Post.class);

        doReturn(true).when(userRepository).existsById(userDTO.getId());
        doReturn(false).when(postRepository).existsByIdAndUser(postDTO.getId(), user);
        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());

        try{
            PostDTO result =  postService.updatePost(userDTO.getId(), postDTO.getId(), postUpdatedDTO);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }






}
