package com.zolostays.instagram.service;


import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.sampleData.userData.UserData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
public class UserServiceTest {


    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository usersRepository;


    @Test
    public void createUserSuccess(){

        UserDTO userDTO = UserData.getNewUserDTO();
        User user = modelMapper.map(userDTO, User.class);
        given(usersRepository.save(user)).willReturn(user);
        given(usersRepository.existsByUsername(user.getUsername())).willReturn(false);
        Optional<UserDTO>  userDTOOptional = userService.addUser(userDTO);

        assertEquals(Optional.of(userDTO), userDTOOptional);
        verify(usersRepository).save(any(User.class));
    }

    @Test
    public void createUserFailed(){

        UserDTO userDTO = UserData.getNewUserDTO();
        User user = modelMapper.map(userDTO, User.class);

        given(usersRepository.existsByUsername(user.getUsername())).willReturn(true);
        Optional<UserDTO>  userDTOOptional = userService.addUser(userDTO);

        assertEquals(Optional.empty(), userDTOOptional);
    }

    @Test
    public void getUserFromIdSuccess(){
        UserDTO userDTO = UserData.getNewUserDTO();
        User user = modelMapper.map(userDTO, User.class);

        doReturn(Optional.of(user)).when(usersRepository).findById(1L);

        Optional<UserDTO> userDTOOptional = userService.getUser(1l);
        UserDTO resultUserDTO = userDTOOptional.get();

        assertEquals(userDTO.getUsername(), resultUserDTO.getUsername());
    }

    @Test
    public void getUserFromIdFailed(){
        doReturn(Optional.empty()).when(usersRepository).findById(1L);

        Optional<UserDTO> userDTOOptional = userService.getUser(1l);
        assertEquals(Optional.empty(), userDTOOptional);
    }

    @Test
    public void deleteUserSuccess(){

        doReturn(true).when(usersRepository).existsById(1l);
        doNothing().when(usersRepository).deleteById(1l);

        boolean isDeleted = userService.deleteUser(1l);

        assertEquals(true, isDeleted);
    }


    @Test
    public void deleteUserFailed(){
        doReturn(false).when(usersRepository).existsById(1l);
        boolean isDeleted = userService.deleteUser(1l);
        assertEquals(false, isDeleted);
    }


    @Test
    public void updateUserSuccess(){
        UserDTO userDTO = UserData.getNewUserDTO();
        User user = modelMapper.map(userDTO, User.class);

        UserDTO userDTOUpdated = UserData.getNewUpdateUserDTO();
        User userUpdated = modelMapper.map(userDTOUpdated, User.class);

        doReturn(Optional.of(user)).when(usersRepository).findById(1l);
        doReturn(userUpdated).when(usersRepository).save(user);

        Optional<UserDTO> optionalUser = userService.updateUser(userDTO, 1L);

        assertEquals(Optional.of(userDTOUpdated), optionalUser);

    }

    @Test
    public void updateUserFailed(){
        UserDTO userDTO = UserData.getNewUserDTO();
        User user = modelMapper.map(userDTO, User.class);

        UserDTO userDTOUpdated = UserData.getNewUpdateUserDTO();
        User userUpdated = modelMapper.map(userDTOUpdated, User.class);

        doReturn(Optional.empty()).when(usersRepository).findById(1l);

        Optional<UserDTO> optionalUser = userService.updateUser(userDTO, 1L);

        assertEquals(Optional.empty(), optionalUser );

    }

}
