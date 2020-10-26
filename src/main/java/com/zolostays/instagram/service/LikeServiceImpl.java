package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.LikeDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.PostDoesNotExistException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.Like;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.LikeRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class LikeServiceImpl implements ILikeService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    ModelMapper modelMapper = new ModelMapper();

    public LikeServiceImpl(){
        modelMapper.typeMap(Like.class, LikeDTO.class).addMapping(
                Like::getPostId, LikeDTO::setPost_DTO
        ).addMapping(Like::getUser, LikeDTO::setUser_DTO);
        modelMapper.typeMap(Post.class, PostDTO.class).addMapping(
                Post::getUser, PostDTO::setUser_DTO
        ).addMapping(Post::getImageList, PostDTO::setList_image_DTO);
    }

    @Transactional
    @Override
    public Optional<LikeDTO> likePost(Long user_id, Long post_id) throws BaseException {

        Optional<User> userOptional = userRepository.findById(user_id);
        if(userOptional.isEmpty()){
            throw new UserDoesNotExistException(user_id.toString());
        }
        Optional<Post> postOptional = postRepository.findById(post_id);
        if(postOptional.isEmpty()){
            throw new PostDoesNotExistException(post_id.toString());
        }

        if(likeRepository.existsByUserAndPostId(userOptional.get(), postOptional.get())){
            return Optional.empty();
        }
        Like like = new Like();
        like.setUser(userOptional.get());
        like.setPostId(postOptional.get());
        return Optional.of(modelMapper.map(likeRepository.save(like), LikeDTO.class));
    }

    @Override
    public List<LikeDTO> getAllLike(Long user_id, Long post_id) throws BaseException{

        Optional<User> userOptional = userRepository.findById(user_id);

        if(userOptional.isEmpty()){
            throw new UserDoesNotExistException(user_id.toString());
        }
        Optional<Post> postOptional = postRepository.findById(post_id);
        if(postOptional.isEmpty()){
            throw new PostDoesNotExistException(post_id.toString());
        }

        List<Like> likeList = likeRepository.findAllByPostId(postOptional.get());
        Type listType = new TypeToken<List<LikeDTO>>(){}.getType();
        List<LikeDTO> likeDTOList = modelMapper.map(likeList, listType);
        return likeDTOList;
    }

    @Override
    @Transactional
    public boolean dislikePost(Long user_id, Long post_id) throws BaseException {

        Optional<User> userOptional = userRepository.findById(user_id);

        if(userOptional.isEmpty()){
            throw new UserDoesNotExistException(user_id.toString());
        }
        Optional<Post> postOptional = postRepository.findById(post_id);
        if(postOptional.isEmpty()){
            throw new PostDoesNotExistException(post_id.toString());
        }
        if(likeRepository.existsByUserAndPostId(userOptional.get(), postOptional.get())){
            likeRepository.deleteByPostIdAndUser(postOptional.get(), userOptional.get());
            return true;
        }
        return  false;
    }
}
