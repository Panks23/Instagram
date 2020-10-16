package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ImageDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PostServiceImpl implements IPostService{

    private PostRepository postRepository;
    private ImageRepository imageRepository;
    private UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public PostServiceImpl(PostRepository postRepository, ImageRepository imageRepository
                            , UserRepository userRepository){
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        modelMapper.typeMap(Post.class, PostDTO.class).addMapping(Post::getUser, PostDTO::setUser_DTO)
                .addMapping(Post::getImageList, PostDTO::setList_image_DTO);
    }


    //TODO check if user has permission
    @Override
    public ResponseDTO<Optional<PostDTO>> getPost(Long post_id) {
        Optional<Post> optionalPost = postRepository.findById(post_id);
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
    public ResponseDTO deletePost(Long post_id) {
        if(postRepository.existsById(post_id)) {
            postRepository.deleteById(post_id);
            return Mapper.objectDeleted("You have deleted object");
        }else{
            return Mapper.objectDoesNotExist("Post doesn't exist by the given post id");
        }
    }

    //TODO why timestamp is null
    @Override
    @Transactional
    public ResponseDTO createPost(PostDTO postDTO, Long user_id) {
        List<ImageDTO> listImageDTO = postDTO.getList_image_DTO();
        Post post = modelMapper.map(postDTO, Post.class);
        Optional<User> userOptional = userRepository.findById(user_id);
        if(userOptional.isPresent()){
            post.setUser(userOptional.get());
            post = savePost(post);
            List<ImageDTO> imageDTOList = saveImageForPost(post, listImageDTO);
            PostDTO resultPostDTO = modelMapper.map(post, PostDTO.class);
            resultPostDTO.setList_image_DTO(imageDTOList);
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
    public ResponseDTO updatePost(PostDTO postDTO, Long user_id, Long post_id) {
        return postRepository.findById(post_id).map(post -> {
                Optional<User> optionalUser = userRepository.findById(user_id);
                if(optionalUser.isPresent()){
                    return updatePostWithGivenField(optionalUser.get(), post, postDTO);
                }else {
                    return  Mapper.responseDTO(new ArrayList<>(), "User doesn't exist to update that post");
                }
        }).orElse(Mapper.responseDTONotFound(new ArrayList<>(), "No such post exist to be updated"));
    }

    //Update of image is not allowed here as Instagram also doesn't allow
    public ResponseDTO updatePostWithGivenField(User user, Post post, PostDTO postDTO){
        if(user.equals(post.getUser())){
            if(postDTO.getCaption()!=null){
                post.setCaption(postDTO.getCaption());
            }
           return  Mapper.responseDTOSingle(postRepository.save(post), "Your post has been updated");
        }else{
             return Mapper.responseDTO(new ArrayList<>(), "Post created by user doesn't match with the user who want to update post");
        }
    }
}
