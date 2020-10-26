package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.FollowMapDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.TargetUserDoesNotExistException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.FollowMap;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.FollowMapRepository;
import com.zolostays.instagram.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Component
public class FollowServiceImpl implements IFollowService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowMapRepository followMapRepository;

    ModelMapper modelMapper = new ModelMapper();

    public FollowServiceImpl(){
        modelMapper.typeMap(FollowMap.class, FollowMapDTO.class).addMapping(FollowMap::getFollowedBy, FollowMapDTO::setFollowed_by)
                .addMapping(FollowMap::getFollowedTo, FollowMapDTO::setFollowed_to);
    }


    @Override
    public Optional<FollowMapDTO> followUser(Long user_id, Long follow_id) throws BaseException {

       checkUserAndTargetUserExistOrNotThrowException(user_id, follow_id);

       User user = userRepository.findById(user_id).get();
       User targetUser = userRepository.findById(follow_id).get();

       if(!followMapRepository.existsByFollowedByAndFollowedTo(user, targetUser)){
           return Optional.of(modelMapper.map(followMapRepository.save(new FollowMap().setFollowedBy(user).setFollowedTo(targetUser)), FollowMapDTO.class));
       }
       return Optional.empty();
    }


    @Override
    @Transactional
    public boolean unfollowUser(Long user_id, Long follow_id) throws BaseException {

        checkUserAndTargetUserExistOrNotThrowException(user_id, follow_id);

        User user = userRepository.findById(user_id).get();
        User targetUser = userRepository.findById(follow_id).get();

        followMapRepository.deleteByFollowedByAndFollowedTo(user, targetUser);
        return true;
    }

    @Override
    public List<FollowMapDTO> getFollower(Long user_id) throws UserDoesNotExistException{
       return userRepository.findById(user_id).map(user -> {
           List<FollowMap> followers = followMapRepository.findAllByFollowedTo(user);
           Type listOfType = new TypeToken<List<FollowMapDTO>>(){}.getType();
           List<FollowMapDTO> followerMapDTOList = modelMapper.map(followers, listOfType);
            return followerMapDTOList;
       }).orElseThrow(()-> new UserDoesNotExistException(user_id.toString()));
    }

    @Override
    public List<FollowMapDTO> getFollowing(Long user_id) throws  UserDoesNotExistException{
        return userRepository.findById(user_id).map(user -> {
            List<FollowMap> followings = followMapRepository.findAllByFollowedBy(user);
            Type listOfType = new TypeToken<List<FollowMapDTO>>(){}.getType();
            List<FollowMapDTO> followingMapDTOList = modelMapper.map(followings, listOfType);
            return followingMapDTOList;
        }).orElseThrow(()->new UserDoesNotExistException(user_id.toString()));
    }


    public boolean checkUserAndTargetUserExistOrNotThrowException(Long user_id, Long follow_id) throws BaseException{
        if(!userRepository.existsById(user_id)){
            throw new UserDoesNotExistException(user_id.toString());
        }
        if(!userRepository.existsById(follow_id)){
            throw new TargetUserDoesNotExistException(follow_id.toString());
        }
        return true;
    }
}
