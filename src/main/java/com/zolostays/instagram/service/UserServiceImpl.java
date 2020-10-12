package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;


@Component
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public  ResponseDTO<Optional<UserDTO>> getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        UserDTO userDTO;
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            userDTO = modelMapper.map(user, UserDTO.class);
        }else{
            userDTO = null;
        }
        Optional<UserDTO> userDTOOptional = Optional.ofNullable(userDTO);
        ResponseDTO<Optional<UserDTO>> responseDTO = Mapper.responseDTOSingle(userDTOOptional);
        return responseDTO;
    }

    @Override
    public ResponseDTO deleteUser(Long id) {
        userRepository.deleteById(id);
        return Mapper.responseDTO(new ArrayList<>());
    }

    @Override
    public ResponseDTO addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user = userRepository.save(user);
        UserDTO resultUserDTO = modelMapper.map(user, UserDTO.class);
        return Mapper.responseDTOSingle(resultUserDTO);
    }

    @Override
    public ResponseDTO updateUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user = userRepository.save(user);
        UserDTO resultUserDTO = modelMapper.map(user, UserDTO.class);
        return Mapper.responseDTOSingle(resultUserDTO);
    }
}
