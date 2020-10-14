package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ImageDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.model.Image;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.ImageRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PostServiceImpl implements IPostService{

    private PostRepository postRepository;
    private ImageRepository imageRepository;
    private IUserService userService;
    private UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public PostServiceImpl(PostRepository postRepository, IUserService userService, ImageRepository imageRepository
                            , UserRepository userRepository){
        this.postRepository = postRepository;
        this.userService = userService;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ResponseDTO<Optional<PostDTO>> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            modelMapper.typeMap(Post.class, PostDTO.class).addMapping(Post::getUser, PostDTO::setUserDTO)
                    .addMapping(Post::getImageList, PostDTO::setListImageDTO);
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            return Mapper.responseDTOSingle(postDTO, "You have got the response for the given post id");
        }
        return Mapper.objectDoesNotExist("Post doesn't exist for the given ID");
    }

    @Override
    public ResponseDTO getAllPost(Long user_id) {
        User user = userRepository.findById(user_id).get();
        List<Post> listOfPost = postRepository.findAllByUser(user);
        return Mapper.responseDTO(listOfPost, "You have recieved post for the given id");
    }

    @Override
    @Transactional
    public ResponseDTO deletePost(Long id) {
        Post post = postRepository.findById(id).get();
        imageRepository.deleteByPost(post);
        postRepository.deleteById(id);
        return Mapper.objectDeleted("You have deleted object");
    }

    @Override
    public ResponseDTO createPost(PostDTO postDTO, Long id) {
        List<ImageDTO> listImageDTO = postDTO.getListImageDTO();
        Post post = modelMapper.map(postDTO, Post.class);
        Optional<UserDTO> userDTO = userService.getUser(id).getResult().get(0);
        if(userDTO.isPresent()){
            User user = modelMapper.map(userDTO.get(), User.class);
            post.setUser(user);
            post = postRepository.save(post);
            Post finalPost = post;
            List<Image> listOfimage = listImageDTO.stream().map(imageDTO -> {
                        Image image = modelMapper.map(imageDTO, Image.class);
                        image.setPost(finalPost);
                        return image;
                    })
                    .collect(
                            Collectors.toList()
                    );
            listOfimage = imageRepository.saveAll(listOfimage);
            PostDTO resultPostDTO = modelMapper.map(post, PostDTO.class);
            listImageDTO = listOfimage.stream().map(image ->
                    modelMapper.map(image, ImageDTO.class) )
                    .collect(
                            Collectors.toList()
                    );
            resultPostDTO.setUserDTO(userDTO.get());
            resultPostDTO.setListImageDTO(listImageDTO);
            return Mapper.responseDTOSingle(resultPostDTO, "Your Post has been created");
        }
        return Mapper.objectDoesNotExist("User doesn't exist to create post for the given user id");
    }

    @Override
    public ResponseDTO updatePost(PostDTO postDTO, Long id) {
        return createPost(postDTO, id);
    }
}
