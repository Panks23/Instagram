package com.zolostays.instagram.controller;


import com.zolostays.instagram.InstagramApplication;
import com.zolostays.instagram.dto.UserDTO;
import com.zolostays.instagram.sampleData.TestUtils;
import com.zolostays.instagram.sampleData.userData.UserData;
import com.zolostays.instagram.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InstagramApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    @Mock
    IUserService userService;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    public void createUserAndReturnSuccessResponseTest() throws Exception {
        UserDTO userDTO = UserData.getNewUserDTO();
        mockMvc.perform(MockMvcRequestBuilders.post("/instagram/api/v1/user")
                                                .content(TestUtils.convertToJsonString(userDTO))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON))
                                                .andExpect(status().isOk())
                                                .andReturn();
    }


    @Test
    public void updateUserAndReturnSuccessResponseTest() throws Exception {
        UserDTO userDTO = UserData.getNewUpdateUserDTO();
        mockMvc.perform(MockMvcRequestBuilders.put("/instagram/api/v1/user/1")
                                                .content(TestUtils.convertToJsonString(userDTO))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON))
                                                .andExpect(status().isOk())
                                                .andReturn();
    }

    //Todo Write a good test test
    @Test
   public void getUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/instagram/api/v1/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Todo Write a good test test
    @Test
    public void deleteUserTest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/instagram/api/v1/user/2")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }
}

