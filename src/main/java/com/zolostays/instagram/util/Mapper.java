package com.zolostays.instagram.util;

import com.zolostays.instagram.dto.ResponseDTO;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static ResponseDTO responseDTOSingle(Object o, String message){
        List<Object> result = new ArrayList<>();
        result.add(o);
        return new ResponseDTO().setStatus(200).setMessage(message)
                .setCount(result.size()).setApi_element("").setResult(result);
    }


    public static ResponseDTO responseDTO(List<?> result, String message){
        return new ResponseDTO().setStatus(200).setMessage(message)
                .setCount(result.size()).setApi_element("").setResult(result);
    }

    public static ResponseDTO responseDTONotFound(List<?> result, String message){
        return new ResponseDTO().setStatus(404).setMessage(message)
                .setCount(result.size()).setApi_element("").setResult(result);
    }


    public static ResponseDTO objectDoesNotExist(String message){
        return new ResponseDTO().setStatus(401).setMessage(message)
                .setResult(new ArrayList()).setCount(0).setApi_element("");
    }

    public static ResponseDTO objectDeleted(String message){
        return new ResponseDTO().setApi_element("").setCount(0).setResult(new ArrayList()).setMessage(message)
                .setStatus(200);
    }

    public static ResponseDTO objectNotCreated(String message){
        return new ResponseDTO().setApi_element("").setCount(0).setResult(new ArrayList()).setMessage(message)
                .setStatus(400);
    }

//    public static List<?> mapList(List<?> source, List<?> target){
//        ModelMapper modelMapper = new ModelMapper();
//        return source.stream().map(items -> modelMapper.map(items, target.getClass()))
//    }

}
