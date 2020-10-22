package com.zolostays.instagram.service;


import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<UserDTO> getUser(Long id);

    Optional<User> getUserById(Long id);

    void deleteUser(Long id) throws UserDoesNotExistException;

    Optional<UserDTO> addUser(UserDTO userDTO);

    Optional<UserDTO> updateUser(UserDTO userDTO, Long user_id);


}
