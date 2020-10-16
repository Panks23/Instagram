package com.zolostays.instagram.service;


import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.model.User;

import java.util.Optional;

public interface IUserService {

    ResponseDTO<Optional<UserDTO>> getUser(Long id);

    Optional<User> getUserById(Long id);

    ResponseDTO deleteUser(Long id);

    ResponseDTO addUser(UserDTO userDTO);

    ResponseDTO updateUser(UserDTO userDTO, Long user_id);


}
