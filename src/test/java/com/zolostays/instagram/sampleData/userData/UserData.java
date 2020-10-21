package com.zolostays.instagram.sampleData.userData;

import com.zolostays.instagram.Gender;
import com.zolostays.instagram.dto.UserDTO;

public class UserData {

    public static UserDTO getNewUserDTO(){
        return new UserDTO().setFirst_name("Pankaj")
                .setLast_name("Singh")
                .setGender(Gender.Male)
                .setUsername("hello");
    }

    public static UserDTO getNewUpdateUserDTO(){
        return new UserDTO().setId(101L)
                .setFirst_name("Panky");
    }
}
