package com.zolostays.instagram.controller;


import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.service.IUserService;
import com.zolostays.instagram.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/instagram/api/v1/user")
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseDTO addUser(@RequestBody UserDTO userDTO){
        return userService.addUser(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseDTO getUser(@PathVariable("id") Long id){
        ResponseDTO responseDTO  = userService.getUser(id);
        return responseDTO;
    }

    @PutMapping
    public ResponseDTO updateUser(@RequestBody UserDTO userDTO){
        ResponseDTO responseDTO = userService.updateUser(userDTO);
        return responseDTO;
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteUser(@PathVariable("id") Long id){
        ResponseDTO responseDTO = userService.deleteUser(id);
        return responseDTO;
    }

}
