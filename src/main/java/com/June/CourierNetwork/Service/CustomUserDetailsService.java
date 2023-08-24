//package com.June.CourierNetwork.Service;
//
//import com.June.CourierNetwork.Model.User;
//import com.June.CourierNetwork.Repo.Contract.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//    private final UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findUserByEmail(email);
//        List<String> roles = new ArrayList<>();
//        roles.add("USER");
//        UserDetails userDetails =
//                org.springframework.security.core.userdetails.User.builder()
//                        .username(user.getEmailAddress())
//                        .password(user.getPassword())
//                        .roles(roles.toArray(new String[0]))
//                        .build();
//        return userDetails;
//    }
//}