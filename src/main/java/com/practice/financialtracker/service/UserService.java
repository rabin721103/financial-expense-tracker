package com.practice.financialtracker.service;

import com.practice.financialtracker.model.User;
import com.practice.financialtracker.model.UserDto;
import com.practice.financialtracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(user.getUserId(),user.getUserName(), user.getEmail())).toList();
    }
    public UserDto getUserById (long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserDto(user.getUserId(),user.getUserName(), user.getEmail());
        } else {
            return null;
        }
    }
    public User getUserByUserName (String username){
        Optional<User> optionalUser = userRepository.findUsersByUserName(username);
        if(optionalUser.isPresent()){
            User theUser = optionalUser.get();
            return theUser;
        }else{
            return null;
        }
    }
    public UserDto registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User newUser = userRepository.save(user);
        return new UserDto(newUser.getUserId(), newUser.getUserName(), newUser.getEmail());
    }
    public UserDto updateUser(long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Update properties of the existing user with values from the updated user
            existingUser.setUserId(id);
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setProfession(updatedUser.getProfession());
            User newUser = userRepository.save(existingUser);
            return new UserDto(newUser.getUserId(), newUser.getUserName(), newUser.getEmail());
        } else {
            return null;
        }
    }










}
