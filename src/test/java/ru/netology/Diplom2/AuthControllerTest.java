package ru.netology.Diplom2;


import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.netology.Diplom2.config.JwtRequestFilter;
import ru.netology.Diplom2.config.SecurityConfig;
import ru.netology.Diplom2.controller.UserController;
import ru.netology.Diplom2.entity.User;
import ru.netology.Diplom2.repository.UserRepository;
import ru.netology.Diplom2.service.UserService;
import ru.netology.Diplom2.utils.JwtTokenUtils;

import java.util.Collections;
import java.util.Optional;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {UserController.class, JwtTokenUtils.class})
@Import(WebSecurityConfiguration.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthControllerTest {
    @MockBean
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenUtils jwtTokenUtils;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    @MockBean
    private UserController userController;
    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
     public void loginHandler() throws  Exception{
        User user = new User(1,"user","user","user");
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));

        String token = jwtTokenUtils.generateToken(userDetails);
        assertNotNull(token);
        mockMvc.perform(MockMvcRequestBuilders.get("/list"))
                        .andExpect(MockMvcResultMatchers.header()
                .stringValues("auth-token", token));
    }

}
