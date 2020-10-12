package com.zolostays.instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseDTO<T> {
    private int status;
    private String message;
    private int count;
    private String api_element;
    List<T> result;
}
