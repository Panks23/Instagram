package com.zolostays.instagram.controller;


import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.service.IUserService;
import com.zolostays.instagram.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static net.logstash.logback.argument.StructuredArguments.*;
import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping("/instagram/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseDTO<UserDTO> addUser(@RequestBody UserDTO userDTO){
        Optional<UserDTO> userDTOOptional = userService.addUser(userDTO);
        if(userDTOOptional.isPresent()){
            return Mapper.responseDTOSingle(userDTOOptional, "User Created" );
        }
        return Mapper.responseDTOSingle(null, "Username Already Taken");
    }

    @GetMapping("/{id}")
    public ResponseDTO<UserDTO> getUser(@PathVariable("id") Long id){
        logger.info("User Requested for getting user with id: {}" ,id);
        Optional<UserDTO> userDTOOptional  = userService.getUser(id);
        if(userDTOOptional.isPresent()){
            logger.info("User Recieved", kv("user", userDTOOptional.get()));
            return Mapper.responseDTOSingle(userDTOOptional, "You have got User" );
        }
        logger.error("User doesn't exist with {}", kv("userId", id));
        return Mapper.responseDTOSingle(null, "User Doesn't exist of given ID");
    }

    @PutMapping("/{id}")
    public ResponseDTO updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long user_id){
        Optional<UserDTO> userDTOOptional  = userService.updateUser(userDTO, user_id);
        if(userDTOOptional.isPresent()){
            return Mapper.responseDTOSingle(userDTOOptional, "User updated" );
        }
        return Mapper.responseDTOSingle(null, "User didn't get updated");
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteUser(@PathVariable("id") Long id){
        try{
            userService.deleteUser(id);
            return Mapper.objectDeleted("User deleted");
        }catch (UserDoesNotExistException e) {
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }

}
