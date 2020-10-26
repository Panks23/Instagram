package com.zolostays.instagram.util;

import com.zolostays.instagram.exception.UserDoesNotExistException;
import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    @Autowired
    private UserRepository userRepository;

   public User getUser(Long user_id) throws UserDoesNotExistException{
       return userRepository.findById(user_id).orElseThrow(()->new UserDoesNotExistException(user_id.toString()));
   }
}
