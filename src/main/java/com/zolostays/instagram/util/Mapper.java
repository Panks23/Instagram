package com.zolostays.instagram.util;

import com.zolostays.instagram.dto.ResponseDTO;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Mapper {

    public static ResponseDTO responseDTOSingle(Object o){
        List<Object> result = new ArrayList<>();
        result.add(o);
        return new ResponseDTO().setStatus(200).setMessage("You have got response")
                .setCount(result.size()).setApi_element("").setResult(result);
    }


    public static ResponseDTO responseDTO(List<?> result){
        return new ResponseDTO().setStatus(200).setMessage("You have got response")
                .setCount(result.size()).setApi_element("").setResult(result);
    }

    public static ResponseDTO objectDoesNotExist(){
        return new ResponseDTO().setStatus(401).setMessage("You have got no response please check your api")
                .setResult(new ArrayList()).setCount(0).setApi_element("");
    }

    public static ResponseDTO objectDeleted(){
        return new ResponseDTO().setApi_element("").setCount(0).setResult(new ArrayList()).setMessage("Deleted")
                .setStatus(200);
    }

//    public static List<?> mapList(List<?> source, List<?> target){
//        ModelMapper modelMapper = new ModelMapper();
//        return source.stream().map(items -> modelMapper.map(items, target.getClass()))
//    }

}
