package ru.netology.Diplom2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.Diplom2.dto.UserDTO;
import ru.netology.Diplom2.exeption.AppError;
import ru.netology.Diplom2.service.UserService;
import ru.netology.Diplom2.utils.JwtTokenUtils;

@RestController
@AllArgsConstructor

public class UserController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;




    @PostMapping("/login")

    public ResponseEntity<?> createAuthToken (@RequestBody UserDTO user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),user.getPassword()));
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "некорретный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(user.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }


}
