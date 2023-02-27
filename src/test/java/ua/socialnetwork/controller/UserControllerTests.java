//package ua.socialnetwork.controller;
//
//
//import org.junit.Before;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import ua.socialnetwork.entity.User;
//import ua.socialnetwork.repo.UserRepo;
//import ua.socialnetwork.security.SecurityConfig;
//import ua.socialnetwork.security.SecurityUser;
//import ua.socialnetwork.service.FriendService;
//import ua.socialnetwork.service.PostService;
//import ua.socialnetwork.service.UserService;
//import ua.socialnetwork.service.impl.CustomUserDetailService;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
////@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(UserController.class)
//public class UserControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private PostService postService;
//
//    @MockBean
//    private FriendService friendService;
//    @InjectMocks
//    private UserController userController;
//
//
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        //Used to only init mocks to user controller class, meaning by we are testing controller class, so we are moking that
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
////    @Test
////    public void getUserByUsername_success(){
////
////        Mockito.when(userService.readByUsername(recordOne.getUsername())).thenReturn(recordOne);
////
////        mockMvc.perform();
////
////
////    }
//
////    @Test
////    @WithUserDetails()
////    public void shouldReturnViewWithPrefilledData() throws Exception{
////        when(userService.readByUsername("username123")).thenReturn(recordOne);
////
////        mockMvc.perform(MockMvcRequestBuilders.get("/users/username123"))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.view().name("profile-page"));
////
////
////
////    }
//
//    //    @Test
////    public void create() throws Exception {
////
////        mockMvc.perform(post("/users/create")
////                .param("firstName", "Oksana")
////                .param("lastName", "Shtaiier")
////                .param("username", "shtaiier")
////                .param("email", "a@gmail.com")
////                .andExpect();
////
////
////    }
//    @Test
//    @WithMockUser(username = "q", password = "q", roles = "USER")
//    @DisplayName("Test requesting specific user information by user id in URL")
//    public void getUserByIdTest() throws Exception {
//        User user = new User();
//        //Fields by which I will check correctness
//        user.setFirstName("FirstTest");
//        user.setLastName("LastTest");
//        user.setPassword("q");
//        user.setEmail("email@ua.com");
//        user.setUsername("q");
//        user.setId(444);
//        given(userService.readByUsername(user.getUsername())).willReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/q"))
//                .andExpect(view().name("profile-page"))
//                .andExpect(status().isUnauthorized())
//                .andExpect(model().attributeExists("user"))
//                .andExpect(model().attribute("user", user));
//    }
//
//
//}
