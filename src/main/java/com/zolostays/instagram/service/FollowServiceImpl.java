package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.model.FollowMap;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.FollowMapRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class FollowServiceImpl implements IFollowService {

    private UserRepository userRepository;
    private FollowMapRepository followMapRepository;

    public FollowServiceImpl(UserRepository userRepository, FollowMapRepository followMapRepository){
        this.followMapRepository = followMapRepository;
        this.userRepository = userRepository;
    }

    //TODO if user already follow
    @Override
    public ResponseDTO followUser(Long user_id, Long follow_id) {
        User user = userRepository.findById(user_id).get();
        User targetUser = userRepository.findById(follow_id).get();
        FollowMap followMap = followMapRepository.save(new FollowMap().setFollowedBy(user).setFollowedTo(targetUser));
        return Mapper.responseDTOSingle(followMap, "You have followed");
    }

    @Override
    @Transactional
    public ResponseDTO unfollowUser(Long user_id, Long follow_id) {
        User user = userRepository.findById(user_id).get();
        User targetUser = userRepository.findById(follow_id).get();
        followMapRepository.deleteByFollowedByAndFollowedTo(user, targetUser);
        return Mapper.responseDTO(new ArrayList<>(), "Following Deleted");
    }

    @Override
    public ResponseDTO getFollower(Long user_id) {
        User user = userRepository.findById(user_id).get();
        List<FollowMap> followers = followMapRepository.findAllByFollowedTo(user);
        return Mapper.responseDTO(followers, "Your followers");
    }

    @Override
    public ResponseDTO getFollowing(Long user_id) {
        User user = userRepository.findById(user_id).get();
        List<FollowMap> followings = followMapRepository.findAllByFollowedBy(user);
        return Mapper.responseDTO(followings, "Your following");    }
}
