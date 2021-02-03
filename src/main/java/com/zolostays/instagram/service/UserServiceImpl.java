package com.zolostays.instagram.service;

import com.zolostays.instagram.context.RequestContext;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;


@Component
public class UserServiceImpl implements IUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public  Optional<UserDTO> getUser(Long id) {
        Optional<User> optionalUser = getUserById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            logger.info("User Recieved", kv("user", userDTO));
            return Optional.of(userDTO);
        }else{
            logger.error("User doesn't exist with {}", kv("userId", id));
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserById(Long user_id){
        return userRepository.findById(user_id);
    }

    @Override
    public void deleteUser(Long user_id) throws UserDoesNotExistException{
        if(userRepository.existsById(user_id)){
            userRepository.deleteById(user_id);
            logger.info("User Deleted with {}", kv("userID", user_id));
        }else{
            logger.error("User doesn't exist with given {}", kv("userID", user_id));
            throw new UserDoesNotExistException(user_id.toString());
        }

    }

    @Override
    public Optional<UserDTO> addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        UserDTO userDTO1 = RequestContext.getAttribute("user");
        System.out.println("Request Context Start");
        System.out.println("Request Context Start");
        System.out.println(userDTO1);
        System.out.println("Request Context End");
        if(!userRepository.existsByUsername(user.getUsername())) {
            user = userRepository.save(user);
            UserDTO resultUserDTO = modelMapper.map(user, UserDTO.class);
            logger.info("User created", kv("userDTO", resultUserDTO));
            return Optional.of(resultUserDTO);
        }else{
            logger.error("Failed To create User",kv("userDTO", userDTO) );
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
