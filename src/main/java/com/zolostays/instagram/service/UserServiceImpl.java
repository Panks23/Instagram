package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.UserRepository;
import com.zolostays.instagram.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Optional;


@Component
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public  ResponseDTO getUser(Long id) {
        Optional<User> optionalUser = getUserById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return Mapper.responseDTOSingle(userDTO, "You have got user");
        }else{
            return Mapper.responseDTONotFound(new LinkedList<>(),"User doesn't exist with the given user id");
        }
    }

    @Override
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public ResponseDTO deleteUser(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return Mapper.objectDeleted("You have deleted User");
        }else{
            return Mapper.responseDTONotFound(new LinkedList<>(), "User can't be deleted because it doesn't exist");
        }

    }

    @Override
    public ResponseDTO addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if(!userRepository.existsByUsername(user.getUsername())) {
            user = userRepository.save(user);
            UserDTO resultUserDTO = modelMapper.map(user, UserDTO.class);
            return Mapper.responseDTOSingle(resultUserDTO, "User created");
        }else{
            return Mapper.objectNotCreated("Username Already Exist");
        }
    }

    @Override
    public ResponseDTO updateUser(UserDTO userDTO) {
        if(userRepository.existsById(userDTO.getId())) {
            return Mapper.responseDTOSingle(createOrUpdateUser(userDTO), "User updated");
        }else{
            return Mapper.responseDTOSingle(createOrUpdateUser(userDTO), "User was not existing before so user got created");
        }
    }

    public UserDTO createOrUpdateUser(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        System.out.println(user);
        user = userRepository.save(user);
        UserDTO resultUserDTO = modelMapper.map(user, UserDTO.class);
        return resultUserDTO;
    }
}
