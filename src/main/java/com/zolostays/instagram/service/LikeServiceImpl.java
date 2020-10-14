package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.model.Like;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.LikeRepository;
import com.zolostays.instagram.repository.PostRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;


@Component
public class LikeServiceImpl implements ILikeService{

    private PostRepository postRepository;
    private UserRepository userRepository;
    private LikeRepository likeRepository;

    public LikeServiceImpl(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }
    @Override
    public ResponseDTO likePost(Long user_id, Long post_id) {
        if(userRepository.existsById(user_id)) {
            if (postRepository.existsById(post_id)) {
                Post post = postRepository.findById(post_id).get();
                User user = userRepository.findById(user_id).get();
                Like like = new Like();
                like.setPostId(post);
                like.setUser(user);
                return Mapper.responseDTOSingle(likeRepository.save(like), "You have liked the post");
            }else{
                return Mapper.objectDoesNotExist("Post doesn't exist of given post id");
            }
        }else{
            return Mapper.objectDoesNotExist("User doesn't exist who is supposed to like the post");
        }
    }

    @Override
    public ResponseDTO getAllLike(Long post_id) {
        return postRepository.findById(post_id).map(post -> {
            return Mapper.responseDTO(likeRepository.findAllByPostId(post), "You have got all the likes");
        }).orElse(Mapper.objectDoesNotExist("Post Doesn't Exist"));

    }

    @Override
    @Transactional
    public ResponseDTO dislikePost(Long user_id, Long post_id) {
        return postRepository.findById(post_id).map(post -> {
            Optional<User> optionalUser = userRepository.findById(user_id);
            if (optionalUser.isPresent()){
                likeRepository.deleteByPostIdAndUser(post, optionalUser.get());
                return Mapper.responseDTOSingle(new ArrayList<>(),"You disliked the post");
            }else {
                return Mapper.responseDTONotFound(new ArrayList<>(), "User doesn't exist");
            }
        }).orElse(Mapper.objectDoesNotExist("No such post exist"));
    }
}
