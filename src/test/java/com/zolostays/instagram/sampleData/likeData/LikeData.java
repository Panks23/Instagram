package com.zolostays.instagram.sampleData.likeData;

import com.zolostays.instagram.dto.LikeDTO;
import com.zolostays.instagram.sampleData.postData.PostData;
import com.zolostays.instagram.sampleData.userData.UserData;

public class LikeData {

    public static LikeDTO getLikeData(){
        return new LikeDTO().setUser_DTO(UserData.getNewUserDTO())
                .setPost_DTO(PostData.getNewPostDTO());
    }
}
