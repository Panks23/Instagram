package com.zolostays.instagram.repositoryTest;


import com.zolostays.instagram.model.Post;
import com.zolostays.instagram.repository.PostRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class PostRepositoryUnitTest {
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Test
//    public void findAllByUserTest(){
//        Post post = postRepository.findById(19L).get();
//        Assert.assertEquals("There should be atleast one post with a user", true, postRepository.findAllByUser(post.getUser()).size()>=1);
//    }
//}
