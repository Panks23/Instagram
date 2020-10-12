package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostServiceImpl implements IPostService{

    private PostRepository postRepository;
    ModelMapper modelMapper = new ModelMapper();

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
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
    public ResponseDTO createPost(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        post = postRepository.save(post);
        return Mapper.responseDTOSingle(post);
    }

    @Override
    public ResponseDTO updatePost(PostDTO userDTO) {
        return null;
    }
}
