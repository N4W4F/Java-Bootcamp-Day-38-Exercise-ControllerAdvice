package com.example.smartmetro.Service;

import com.example.smartmetro.ApiResponse.ApiException;
import com.example.smartmetro.Model.User;
import com.example.smartmetro.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null)
            throw new ApiException("User with ID: " + id + " was not found");

        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setPreference(user.getPreference());
        oldUser.setBalance(user.getBalance());
        userRepository.save(oldUser);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null)
            throw new ApiException("User with ID: " + id + " was not found");

        userRepository.delete(user);
    }
    // CRUD - END

    // Getters
    public User getUserById(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null)
            throw new ApiException("User with ID: " + id + " was not found");

        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new ApiException("User with this email was not found");

        return user;
    }

    // Services
    public void transferToUser(Integer senderId, Double amount, String receiverEmail) {
        User sender = userRepository.findUserById(senderId);
        if (sender == null)
            throw new ApiException("Sender was not found");

        User receiver = userRepository.findUserByEmail(receiverEmail);
        if (receiver == null)
            throw new ApiException("Receiver was not found");

        if (sender.getBalance() >= amount) {
            receiver.setBalance(receiver.getBalance() + amount);
            sender.setBalance(sender.getBalance() - amount);
            userRepository.save(receiver);
            userRepository.save(sender);
            return;
        }
        throw new ApiException("You don't have enough funds");
    }
}
