package com.zolostays.instagram.sampleData.postData;

import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.sampleData.userData.UserData;
import java.util.Arrays;

public class PostData {

    public static PostDTO getNewPostDTO(){
        return new PostDTO().setId(1l).setCaption("Hi Caption")
                .setUser_DTO(UserData.getNewUserDTO())
                .setList_image_DTO(Arrays.asList(ImageData.getNewImageDTO()));
    }

    public PostDTO getUpdatedPostDTO(){
        return new PostDTO().setId(1l).setCaption("Hi Caption Updated")
                .setUser_DTO(UserData.getNewUserDTO())
                .setList_image_DTO(Arrays.asList(ImageData.getNewImageDTO()));
    }
}
