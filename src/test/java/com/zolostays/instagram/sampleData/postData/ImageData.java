package com.zolostays.instagram.sampleData.postData;

import com.zolostays.instagram.dto.ImageDTO;

public class ImageData {

    public static ImageDTO getNewImageDTO(){
        return new ImageDTO().setImage_url("https://www.google.com").setId(1l);
    }
}
