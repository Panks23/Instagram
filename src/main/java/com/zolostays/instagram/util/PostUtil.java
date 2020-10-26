package com.zolostays.instagram.util;

import com.zolostays.instagram.exception.PostDoesNotExistException;
import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostUtil {

    @Autowired
    private PostRepository postRepository;

    public Post getPost(Long post_id) throws PostDoesNotExistException {
        return postRepository.findById(post_id).map(post -> {
            return post;
        }).orElseThrow(()-> new PostDoesNotExistException(post_id.toString()));
    }
}
