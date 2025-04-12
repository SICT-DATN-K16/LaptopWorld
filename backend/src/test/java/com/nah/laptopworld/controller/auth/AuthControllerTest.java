// package com.nah.laptopworld.controller.auth;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.nah.laptopworld.dto.response.LoginResponse;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.MockitoAnnotations;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @ExtendWith(SpringExtension.class)
// @AutoConfigureMockMvc(addFilters = false)
// @WebMvcTest(AuthController.class)
// class AuthControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @MockBean
//     private AuthenticationManager authenticationManager;

//     @InjectMocks
//     private AuthController authController;

//     @BeforeEach
//     public void setup() {
//         MockitoAnnotations.openMocks(this);
//         authController = new AuthController(authenticationManager);
//     }

//     @Test
//     public void loginSuccess() throws Exception {
//         LoginResponse loginResponse = new LoginResponse();
//         loginResponse.setUsername("testuser@gmail.com");
//         loginResponse.setPassword("123456");

//         mockMvc.perform(post("/api/auth/login")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(loginResponse)))
//                 .andExpect(status().isOk());
//     }

//     @Test
//     public void loginFailed() throws Exception {
//         LoginResponse loginResponse = new LoginResponse();
//         loginResponse.setUsername("testuser123@gmail.com");
//         loginResponse.setPassword("123456aaa");

//         mockMvc.perform(post("/api/auth/login")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(loginResponse)))
//                 .andExpect(status().isUnauthorized());
//     }
// }