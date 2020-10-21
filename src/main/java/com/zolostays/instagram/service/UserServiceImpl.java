package com.zolostays.instagram.service;

import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Component
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public  Optional<UserDTO> getUser(Long id) {
        Optional<User> optionalUser = getUserById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return Optional.of(userDTO);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public boolean deleteUser(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Optional<UserDTO> addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if(!userRepository.existsByUsername(user.getUsername())) {
            user = userRepository.save(user);
            UserDTO resultUserDTO = modelMapper.map(user, UserDTO.class);
            return Optional.of(resultUserDTO);
        }else{
            return Optional.empty();
        }
    }


        @Override
        public Optional<UserDTO> updateUser(UserDTO userDTO, Long user_id) {
        return userRepository.findById(user_id).map(user -> {
            setValueOfUserWithGivenField(user, userDTO);
            return Optional.of(modelMapper.map(userRepository.save(user), UserDTO.class));
        }).orElse(Optional.empty());
    }


    public User setValueOfUserWithGivenField(User user, UserDTO userDTO){
        if(userDTO.getFirst_name()!=null){
            user.setFirst_name(userDTO.getFirst_name());
        }
        if(userDTO.getLast_name()!=null){
            user.setLast_name(userDTO.getLast_name());
        }
        if(userDTO.getDob()!=null){
            user.setDob(userDTO.getDob());
        }
        if(userDTO.getGender()!=null){
            user.setGender(userDTO.getGender());
        }
        if(userDTO.getPhoneNo()!=0){
            user.setPhoneNo(userDTO.getPhoneNo());
        }
        return user;
    }

}
