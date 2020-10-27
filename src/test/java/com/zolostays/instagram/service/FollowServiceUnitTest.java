package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.FollowMapDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.FollowMap;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.FollowMapRepository;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.sampleData.followData.FollowData;
import com.zolostays.instagram.sampleData.userData.UserData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.PushBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class FollowServiceUnitTest {

    @InjectMocks
    private FollowServiceImpl followService;

    @Mock
    private UserRepository  userRepository;

    @Mock
    private FollowMapRepository followMapRepository;

    ModelMapper modelMapper = new ModelMapper();

    UserDTO userDTO;

    User user;

    User targetUser;

    UserDTO targetUserDTO;

    FollowMapDTO followMapDTO;

    FollowMap followMap;

    @Before
    public void setUp(){

        modelMapper.typeMap(FollowMapDTO.class, FollowMap.class).addMapping(FollowMapDTO::getFollowed_by, FollowMap::setFollowedBy)
                .addMapping(FollowMapDTO::getFollowed_to, FollowMap::setFollowedTo);
        userDTO = UserData.getNewUserDTO();
        user = modelMapper.map(userDTO, User.class);
        followMapDTO = FollowData.getFollowData();
        followMap = modelMapper.map(followMapDTO, FollowMap.class);

        targetUserDTO = followMapDTO.getFollowed_to();
        targetUser = modelMapper.map(targetUserDTO, User.class);

    }

    @Test
    public void followUserSuccess(){

        given(userRepository.existsById(userDTO.getId())).willReturn(true);
        given(userRepository.existsById(targetUser.getId())).willReturn(true);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(userRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        given(followMapRepository.existsByFollowedByAndFollowedTo(user, targetUser)).willReturn(false);
        given(followMapRepository.save(followMap.setId(null))).willReturn(followMap);

        try {
            Optional<FollowMapDTO> result = followService.followUser(userDTO.getId(), targetUserDTO.getId());
            result.get().setId(1l);
            assertEquals(Optional.of(followMapDTO), result);
            followMap.setId(1l);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }

    @Test
    public void followUserShouldThrowUserDoesNotExistException(){
        given(userRepository.existsById(userDTO.getId())).willReturn(false);
        given(userRepository.existsById(targetUser.getId())).willReturn(true);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(userRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        given(followMapRepository.existsByFollowedByAndFollowedTo(user, targetUser)).willReturn(false);
        given(followMapRepository.save(followMap.setId(null))).willReturn(followMap);
        followMap.setId(1l);

        try {
            Optional<FollowMapDTO> result = followService.followUser(userDTO.getId(), targetUserDTO.getId());
            result.get().setId(1l);
            assertEquals(Optional.of(followMapDTO), result);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void followUserShouldThrowTargetUserDoesNotExistException(){
        given(userRepository.existsById(userDTO.getId())).willReturn(true);
        given(userRepository.existsById(targetUser.getId())).willReturn(false);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(userRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        given(followMapRepository.existsByFollowedByAndFollowedTo(user, targetUser)).willReturn(false);
        given(followMapRepository.save(followMap.setId(null))).willReturn(followMap);
        followMap.setId(1l);

        try {
            Optional<FollowMapDTO> result = followService.followUser(userDTO.getId(), targetUserDTO.getId());
            result.get().setId(1l);
            assertEquals(Optional.of(followMapDTO), result);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void followUserShouldReturnOptionalEmptyAsItAlreadyFollow(){
        given(userRepository.existsById(userDTO.getId())).willReturn(true);
        given(userRepository.existsById(targetUser.getId())).willReturn(true);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(userRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        given(followMapRepository.existsByFollowedByAndFollowedTo(user, targetUser)).willReturn(true);

        try {
            Optional<FollowMapDTO> result = followService.followUser(userDTO.getId(), targetUserDTO.getId());
            assertEquals(Optional.empty(), result);
        }catch (BaseException baseException){
            assertTrue(false);
        }
    }


    @Test
    public void unfollowUserSuccess(){
        given(userRepository.existsById(userDTO.getId())).willReturn(true);
        given(userRepository.existsById(targetUser.getId())).willReturn(true);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(userRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        doNothing().when(followMapRepository).deleteByFollowedByAndFollowedTo(user, targetUser);

        try {
            boolean isDeleted = followService.unfollowUser(userDTO.getId(), targetUserDTO.getId());
            assertTrue(isDeleted);
        }catch (BaseException baseException){
            assertTrue(false);
        }

    }

    @Test
    public void unfollowUserShouldThrowUserDoesNotExistException(){
        given(userRepository.existsById(userDTO.getId())).willReturn(false);
        given(userRepository.existsById(targetUser.getId())).willReturn(true);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(userRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        doNothing().when(followMapRepository).deleteByFollowedByAndFollowedTo(user, targetUser);

        try {
            boolean isDeleted = followService.unfollowUser(userDTO.getId(), targetUserDTO.getId());
            assertTrue(isDeleted);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void unfollowUserShouldThrowTargetUserDoesNotExistException(){
        given(userRepository.existsById(userDTO.getId())).willReturn(true);
        given(userRepository.existsById(targetUser.getId())).willReturn(false);
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(userRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        doNothing().when(followMapRepository).deleteByFollowedByAndFollowedTo(user, targetUser);

        try {
            boolean isDeleted = followService.unfollowUser(userDTO.getId(), targetUserDTO.getId());
            assertTrue(isDeleted);
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }


    @Test
    public void getFollowerSuccess(){
        given(userRepository.findById(targetUserDTO.getId())).willReturn(Optional.of(targetUser));
        given(followMapRepository.findAllByFollowedTo(targetUser)).willReturn((Arrays.asList(followMap)));

        try {
            List<FollowMapDTO> followMapDTOList = followService.getFollower(targetUser.getId());
            assertEquals(Arrays.asList(followMapDTO), followMapDTOList);
        }catch (BaseException baseException){
            assertTrue(false);
        }


    }

    @Test
    public void getFollowerShouldThrowUserDoesNotExistException(){
        given(userRepository.findById(targetUserDTO.getId())).willReturn(Optional.empty());
        try {
            List<FollowMapDTO> followMapDTOList = followService.getFollower(targetUserDTO.getId());
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }

    @Test
    public void getFollowingSuccess(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.of(user));
        given(followMapRepository.findAllByFollowedBy(user)).willReturn(Arrays.asList(followMap));
        try {
            List<FollowMapDTO> followMapDTOList = followService.getFollowing(userDTO.getId());
            assertEquals(Arrays.asList(followMapDTO), followMapDTOList);
        }catch (BaseException baseException){
            assertTrue(false);
        }

    }
    @Test
    public void getFollowingShouldThrowUserDoesNotExistException(){
        given(userRepository.findById(userDTO.getId())).willReturn(Optional.empty());
        try {
            List<FollowMapDTO>  followMapDTOList = followService.getFollowing(userDTO.getId());
            assertTrue(false);
        }catch (BaseException baseException){
            assertTrue(true);
        }
    }



}
