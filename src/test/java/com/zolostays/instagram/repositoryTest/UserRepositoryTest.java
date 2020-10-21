package com.zolostays.instagram.repositoryTest;


import com.zolostays.instagram.model.User;
import com.zolostays.instagram.repository.UserRepository;
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
//public class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void existsByUsernameTest(){
//            User user = userRepository.findById(3L).get();
//            Assert.assertTrue("User exist by Username", userRepository.existsByUsername(user.getUsername()));
//    }
//}
