package com.zolostays.instagram.controller;


import com.zolostays.instagram.annotation.AnnotationTest;
import com.zolostays.instagram.context.RequestContext;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.dto.UserRequestData;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.service.IUserService;
import com.zolostays.instagram.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static net.logstash.logback.argument.StructuredArguments.*;
import java.util.Optional;

@RestController
@RequestMapping("/instagram/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRequestData userRequestData;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    @AnnotationTest(type = 3)
    public ResponseDTO<UserDTO> addUser(){
        logger.info("Got a request to Create User");
        RequestContext.setAttributes("user", userRequestData.getUserDTO());
        Optional<UserDTO> userDTOOptional = userService.addUser(userRequestData.getUserDTO());
        System.out.println("Hi User");
        System.out.println("Hi User");
        System.out.println(userRequestData.getUserDTO());
        if(userDTOOptional.isPresent()){
            return Mapper.responseDTOSingle(userDTOOptional, "User Created" );
        }
        logger.error("Username already Taken");
        return Mapper.responseDTOSingle(null, "Username Already Taken");
    }

    @GetMapping("/{id}")
    @AnnotationTest(type = 3)
    public ResponseDTO<UserDTO> getUser(@PathVariable("id") Long id){
        logger.info("User Requested for getting user with id: {}" ,id);
        Optional<UserDTO> userDTOOptional  = userService.getUser(id);
        if(userDTOOptional.isPresent()){
            return Mapper.responseDTOSingle(userDTOOptional, "You have got User" );
        }
        return Mapper.responseDTOSingle(null, "User Doesn't exist of given ID");
    }

    @PutMapping("/{id}")
    @AnnotationTest(type = 3)
    public ResponseDTO updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long user_id){
        logger.info("User Requested for updating with {}", kv("userID", user_id));
        try {
            Optional<UserDTO> userDTOOptional  = userService.updateUser(userDTO, user_id);
            logger.info("User Updated with {}", kv("userId", user_id));
            return Mapper.responseDTOSingle(userDTOOptional, "User updated" );
        }catch (BaseException baseException){
            logger.error("Failed to update User with {}", kv("userID", user_id));
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }

    }

    @PatchMapping("patch/{id}")
    @AnnotationTest(type = 3)
    public ResponseDTO updateViaPatchUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long user_id){
        logger.info("User Requested for updating with {}", kv("userID", user_id));
        try {
            Optional<UserDTO> userDTOOptional  = userService.updateUser(userDTO, user_id);
            logger.info("User Updated with {}", kv("userId", user_id));
            return Mapper.responseDTOSingle(userDTOOptional, "User updated" );
        }catch (BaseException baseException){
            logger.error("Failed to update User with {}", kv("userID", user_id));
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    @AnnotationTest(type = 3)
    public ResponseDTO deleteUser(@PathVariable("id") Long user_id){
        logger.info("Requested for deleting usere with {}", kv("userID", user_id));
        try{
            userService.deleteUser(user_id);
            return Mapper.objectDeleted("User deleted");
        }catch (UserDoesNotExistException e) {
            logger.error("Anything ", e);
            return Mapper.responseDTOSingle(null, e.getMessage());
        }
    }

}
