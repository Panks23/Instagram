package com.zolostays.instagram.aspect;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zolostays.instagram.annotation.AnnotationTest;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.dto.UserRequestData;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Method;

@Aspect
@Component
public class AspectTestAnnotation {

    @Autowired
    private UserRequestData userRequestData;



    @Before("@annotation(com.zolostays.instagram.annotation.AnnotationTest)")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AnnotationTest annotation = method.getAnnotation(AnnotationTest.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try{
            System.out.println("Hi");
            System.out.println("Hi");
            request.getInputStream();
            System.out.println(request.getInputStream());
            System.out.println("hi");
            System.out.println("hi");
            JsonNode fileType = objectMapper.readValue(request.getInputStream(), JsonNode.class);
            UserDTO userDTO = objectMapper.readValue(fileType.toString(), UserDTO.class);
            userRequestData.setUserDTO(userDTO);
            System.out.println(userDTO);
            System.out.println("Hi");
            System.out.println("Hi");
            System.out.println("Hi");
            System.out.println("Hi");
            System.out.println("Hi");
            System.out.println(fileType);
        }catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Hi There");
            System.out.println("Hi There");
            System.out.println("Hi There");
            System.out.println("Hi There");
        }
    }
}
