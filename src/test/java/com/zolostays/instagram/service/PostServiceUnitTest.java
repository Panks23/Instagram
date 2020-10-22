package com.zolostays.instagram.service;


import com.zolostays.instagram.dto.ImageDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.Image;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.ImageRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.sampleData.postData.ImageData;
import com.zolostays.instagram.sampleData.postData.PostData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

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
        modelMapper.typeMap(Post.class, PostDTO.class).addMapping(Post::getUser, PostDTO::setUser_DTO)
                .addMapping(Post::getImageList, PostDTO::setList_image_DTO);
    }

    @Test
    public void createPostSuccess(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO  = postDTO.getUser_DTO();
        List<ImageDTO> imageDTOList  = Arrays.asList(ImageData.getNewImageDTO());

        User user = modelMapper.map(userDTO, User.class);
        doReturn(Optional.of(user)).when(userRepository).findById(userDTO.getId());
        Post post = modelMapper.map(postDTO, Post.class);
        doReturn(post).when(postRepository).save(post);
        List<Image> listOfimage = imageDTOList.stream().map(imageDTO -> {
            Image image = modelMapper.map(imageDTO, Image.class);
            image.setPost(post);
            return image;
        }).collect(Collectors.toList());
        doReturn(listOfimage).when(imageRepository).saveAll(listOfimage);
        try{
            PostDTO postDTOResult = postService.createPost(postDTO, userDTO.getId());
            assertEquals(postDTO, postDTOResult);
        }catch (UserDoesNotExistException e){
            assertFalse(false);
        }
    }

    @Test
    public void createPostFailed(){
        PostDTO postDTO = PostData.getNewPostDTO();
        UserDTO userDTO  = postDTO.getUser_DTO();

        doReturn(Optional.empty()).when(userRepository).findById(userDTO.getId());
        try{
            PostDTO postDTOResult = postService.createPost(postDTO, userDTO.getId());
            assertFalse(false);
        }catch (UserDoesNotExistException e){
            assertTrue(true);
        }
    }


}