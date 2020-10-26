package com.zolostays.instagram;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.model.Comment;
import com.zolostays.instagram.model.Post;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InstagramApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstagramApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapperBean(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Comment.class, CommentDTO.class).addMapping(Comment::getPost, CommentDTO::setPost_DTO)
                .addMapping(Comment::getUser, CommentDTO::setUser_DTO)
                .addMapping(Comment::getTimeStamp, CommentDTO::setTime_stamp);
        modelMapper.typeMap(Post.class, PostDTO.class).addMapping(
                Post::getUser, PostDTO::setUser_DTO
        ).addMapping(Post::getImageList, PostDTO::setList_image_DTO);
        return modelMapper;
    }

}
