package com.zolostays.instagram.sampleData.followData;

import com.zolostays.instagram.dto.FollowMapDTO;
import com.zolostays.instagram.sampleData.userData.UserData;

public class FollowData {


    public static FollowMapDTO getFollowData(){
        return new FollowMapDTO().setFollowed_by(UserData.getNewUserDTO())
                .setFollowed_to(UserData.getNewUserDTO().setId(2L).setUsername("panks"))
                .setId(1L);
    }
}
