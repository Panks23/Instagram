package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostServiceImpl implements IPostService{

    private PostRepository postRepository;
    private IUserService userService;
    ModelMapper modelMapper = new ModelMapper();

    public PostServiceImpl(PostRepository postRepository, IUserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }


    @Override
    public ResponseDTO<Optional<PostDTO>> getPost(Long id) {
        return null;
    }

    @Override
    public ResponseDTO getAllPost() {
        return null;
    }

    @Override
    public ResponseDTO deletePost(Long id) {
        return null;
    }

    @Override
    public ResponseDTO createPost(PostDTO postDTO, Long id) {
        Post post = modelMapper.map(postDTO, Post.class);
        Optional<UserDTO> userDTO = userService.getUser(id).getResult().get(0);
        if(userDTO.isPresent()){
            User user = modelMapper.map(userDTO.get(), User.class);
            post.setUser(user);
            post = postRepository.save(post);
            //TODO return postDTO
            return Mapper.responseDTOSingle(post);
        }
        return Mapper.objectDoesNotExist();
    }

    @Override
    public ResponseDTO updatePost(PostDTO userDTO) {
        return null;
    }
}
