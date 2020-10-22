package com.zolostays.instagram.service;


import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PostServiceUnitTest {

    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    IPostService postService;

    @Mock
    UserRepository userRepository;

    @Mock
    PostRepository postRepository;


}
