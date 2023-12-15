package com.practice.financialtracker.service;

import com.practice.financialtracker.model.User;
import com.practice.financialtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findUsersByUserName(username);
        // Converting userDetail to UserDetails
             User user =   optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));
        return new CustomUserDetails(user);
    }
}
