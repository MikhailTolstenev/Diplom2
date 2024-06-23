package ru.netology.Diplom2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.Diplom2.entity.User;
import ru.netology.Diplom2.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;




    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user1 = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", login)
        ));

         return new org.springframework.security.core.userdetails.User(
                user1.getLogin(),
                user1.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user1.getRole())));

    }
    public static User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user= User.builder()
                .login(userDetails.getUsername())
                .password(userDetails.getPassword())
                .role((String.valueOf(userDetails.getAuthorities())))
                .build();

        return user;

    }

}
