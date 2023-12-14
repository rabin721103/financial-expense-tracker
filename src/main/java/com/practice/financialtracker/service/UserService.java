package com.practice.financialtracker.service;

import com.practice.financialtracker.model.User;
import com.practice.financialtracker.model.UserDto;
import com.practice.financialtracker.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(user.getUserId(),user.getUserName(), user.getEmail())).toList();
    }
    public User getUserById (long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
    public UserDto registerUser(User user) {
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
            existingUser.setIncome(updatedUser.getIncome());
            User newUser = userRepository.save(existingUser);
            return new UserDto(newUser.getUserId(), newUser.getUserName(), newUser.getEmail());
        } else {
            return null;
        }
    }










}
