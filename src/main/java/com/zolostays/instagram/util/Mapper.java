package com.zolostays.instagram.util;

import com.zolostays.instagram.dto.ResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static ResponseDTO responseDTOSingle(Object o){
        List<Object> result = new ArrayList<>();
        result.add(o);
        return new ResponseDTO().setStatus(200).setMessage("You have got response")
                .setCount(result.size()).setApi_element("").setResult(result);
    }


    public static ResponseDTO responseDTO(List<Object> result){
        return new ResponseDTO().setStatus(200).setMessage("You have got response")
                .setCount(result.size()).setApi_element("").setResult(result);
    }

    public static ResponseDTO objectDoesNotExist(){
        return new ResponseDTO().setStatus(401).setMessage("You have got no response please check your api")
                .setResult(new ArrayList()).setCount(0).setApi_element("");
    }
}
