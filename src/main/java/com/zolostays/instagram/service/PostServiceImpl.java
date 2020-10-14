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
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
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
        modelMapper.typeMap(Post.class, PostDTO.class).addMapping(Post::getUser, PostDTO::setUser_DTO)
                .addMapping(Post::getImageList, PostDTO::setList_image_DTO);
    }


    @Override
    public ResponseDTO<Optional<PostDTO>> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            return Mapper.responseDTOSingle(postDTO, "You have got the response for the given post id");
        }
        return Mapper.objectDoesNotExist("Post doesn't exist for the given ID");
    }

    @Override
    public ResponseDTO getAllPost(Long user_id) {
        if(userRepository.existsById(user_id)) {
            User user = userRepository.findById(user_id).get();
            List<Post> listOfPost = postRepository.findAllByUser(user);
            Type listType = new TypeToken<List<PostDTO>>(){}.getType();
            List<PostDTO> postDTOList = modelMapper.map(listOfPost, listType);
            return Mapper.responseDTO(postDTOList, "You have recieved post for the given user id");
        }else{
            return Mapper.objectDoesNotExist("User doesn't exist");
        }
    }

    @Override
    @Transactional
    public ResponseDTO deletePost(Long id) {
        if(postRepository.existsById(id)) {
            Post post = postRepository.findById(id).get();
            imageRepository.deleteByPost(post);
            postRepository.deleteById(id);
            return Mapper.objectDeleted("You have deleted object");
        }else{
            return Mapper.objectDoesNotExist("Post doesn't exist by the given post id");
        }
    }

    @Override
    public ResponseDTO createPost(PostDTO postDTO, Long id) {
        List<ImageDTO> listImageDTO = postDTO.getList_image_DTO();
        Post post = modelMapper.map(postDTO, Post.class);
        Optional<User> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()){
            Optional<UserDTO> userDTOOptional = Optional.ofNullable(modelMapper.map(userOptional.get(), UserDTO.class));
            User user = modelMapper.map(userDTOOptional.get(), User.class);
            post.setUser(user);
            post = savePost(post);
            PostDTO resultPostDTO = modelMapper.map(post, PostDTO.class);
            listImageDTO = saveImageForPost(post, listImageDTO);
            resultPostDTO.setUser_DTO(userDTOOptional.get());
            resultPostDTO.setList_image_DTO(listImageDTO);
            return Mapper.responseDTOSingle(resultPostDTO, "Your Post has been created");
        }
        return Mapper.objectDoesNotExist("User doesn't exist to create post for the given user id");
    }

    public Post savePost(Post post){
        return postRepository.save(post);
    }

    public List<ImageDTO> saveImageForPost(Post post, List<ImageDTO> listImageDTO){
        List<Image> listOfimage = listImageDTO.stream().map(imageDTO -> {
            Image image = modelMapper.map(imageDTO, Image.class);
            image.setPost(post);
            return image;
        }).collect(Collectors.toList());
        listOfimage = imageRepository.saveAll(listOfimage);
        listImageDTO = listOfimage.stream().map(image ->
                modelMapper.map(image, ImageDTO.class) )
                .collect(
                        Collectors.toList()
                );

        return listImageDTO;
    }

    @Override
    public ResponseDTO updatePost(PostDTO postDTO, Long id) {
        return createPost(postDTO, id);
    }
}
