package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.model.FollowMap;
import com.zolostays.instagram.repository.FollowMapRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class FollowServiceImpl implements IFollowService {

    private UserRepository userRepository;
    private FollowMapRepository followMapRepository;

    public FollowServiceImpl(UserRepository userRepository, FollowMapRepository followMapRepository){
        this.followMapRepository = followMapRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseDTO followUser(Long user_id, Long follow_id) {
        return userRepository.findById(user_id).map(user -> {
            return userRepository.findById(follow_id).map(targetUser->{
                if(!followMapRepository.existsByFollowedByAndFollowedTo(user, targetUser)){
                    FollowMap followMap = followMapRepository.save(new FollowMap().setFollowedBy(user).setFollowedTo(targetUser));
                    return Mapper.responseDTOSingle(followMap, "You have followed");
                }
                return Mapper.responseDTOSingle(followMapRepository.findByFollowedByAndFollowedTo(user, targetUser),"You already Follow");
            }).orElse(Mapper.responseDTO(new ArrayList<>(), "Target User doesn't exist"));
        }).orElse(Mapper.responseDTO(new ArrayList<>(), "User id doesn't exist"));
    }

    @Override
    @Transactional
    public ResponseDTO unfollowUser(Long user_id, Long follow_id) {
        return userRepository.findById(user_id).map(user -> {
            return userRepository.findById(follow_id).map(targetUser->{
                followMapRepository.deleteByFollowedByAndFollowedTo(user, targetUser);
                return Mapper.responseDTO(new ArrayList<>(), "You don't follow anymore");
            }).orElse(Mapper.responseDTO(new ArrayList<>(), "Target User doesn't exist"));
        }).orElse(Mapper.responseDTO(new ArrayList<>(), "User id doesn't exist"));
    }

    @Override
    public ResponseDTO getFollower(Long user_id) {
        return userRepository.findById(user_id).map(user -> {
            List<FollowMap> followers = followMapRepository.findAllByFollowedTo(user);
            return Mapper.responseDTO(followers, "Your followers");
        }).orElse(Mapper.responseDTO(new LinkedList<>(), "User doesn't exist"));

    }

    @Override
    public ResponseDTO getFollowing(Long user_id) {
        return userRepository.findById(user_id).map(user -> {
            List<FollowMap> followings = followMapRepository.findAllByFollowedBy(user);
            return Mapper.responseDTO(followings, "Your following");
        }).orElse(Mapper.responseDTO(new LinkedList<>(), "User doesn't exist"));
    }
}
