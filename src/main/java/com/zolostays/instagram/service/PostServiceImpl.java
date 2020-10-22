package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ImageDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.PostDoesNotExistException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.Image;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.ImageRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PostServiceImpl implements IPostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public PostServiceImpl(){
        modelMapper.typeMap(Post.class, PostDTO.class).addMapping(Post::getUser, PostDTO::setUser_DTO)
                .addMapping(Post::getImageList, PostDTO::setList_image_DTO);
    }


    @Override
    public Optional<PostDTO> getPost(Long user_id, Long post_id) throws BaseException {
        Optional<User> userOptional = userRepository.findById(user_id);
        if(!userOptional.isPresent()){
            throw new UserDoesNotExistException(user_id.toString());
        }
        Optional<Post> optionalPost = postRepository.findByIdAndUser(post_id, userOptional.get());
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            return Optional.of(postDTO);
        }else{
            throw new PostDoesNotExistException(post_id.toString());
        }
    }

    @Override
    public List<PostDTO> getAllPost(Long user_id) throws UserDoesNotExistException {
        if(userRepository.existsById(user_id)) {
            User user = userRepository.findById(user_id).get();
            List<Post> listOfPost = postRepository.findAllByUser(user);
            Type listType = new TypeToken<List<PostDTO>>(){}.getType();
            List<PostDTO> postDTOList = modelMapper.map(listOfPost, listType);
            return postDTOList;
        }else{
            throw new UserDoesNotExistException(user_id.toString());
        }
    }


    @Override
    @Transactional
    public void deletePost(Long user_id, Long post_id) throws BaseException{

        if(!userRepository.existsById(user_id)){
            throw new UserDoesNotExistException(user_id.toString());
        }
        User user = userRepository.findById(user_id).get();
        if(!postRepository.existsByIdAndUser(post_id, user)){
            throw new PostDoesNotExistException(post_id.toString());
        }
        postRepository.deleteByIdAndUser(post_id, user);
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, Long user_id) throws UserDoesNotExistException {
        List<ImageDTO> listImageDTO = postDTO.getList_image_DTO();
        Post post = modelMapper.map(postDTO, Post.class);
        Optional<User> userOptional = userRepository.findById(user_id);
        if(userOptional.isPresent()){
            post.setUser(userOptional.get());
            post = savePost(post);
            List<ImageDTO> imageDTOList = saveImageForPost(post, listImageDTO);
            PostDTO resultPostDTO = modelMapper.map(post, PostDTO.class);
            resultPostDTO.setList_image_DTO(imageDTOList);
            return resultPostDTO;
        }
        else{
            throw new UserDoesNotExistException(user_id.toString());
        }
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
    public PostDTO updatePost(Long user_id, Long post_id, PostDTO postDTO) throws BaseException{
        if(!userRepository.existsById(user_id)){
            throw new UserDoesNotExistException(user_id.toString());
        }
        User user = userRepository.findById(user_id).get();
        if(!postRepository.existsByIdAndUser(post_id, user)){
            throw new PostDoesNotExistException(post_id.toString());
        }
        Post originalPost = postRepository.findByIdAndUser(post_id, user).get();
        Post post  = updatePostWithGivenField(user, originalPost, postDTO);
        return modelMapper.map(post, PostDTO.class);
    }

    public Post updatePostWithGivenField(User user, Post originalPost, PostDTO postDTO){
       originalPost.setCaption(postDTO.getCaption());
       return postRepository.save(originalPost);
    }
}
