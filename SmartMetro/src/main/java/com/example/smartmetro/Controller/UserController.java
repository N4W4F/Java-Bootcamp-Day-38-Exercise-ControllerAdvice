package com.example.smartmetro.Controller;

import com.example.smartmetro.ApiResponse.ApiResponse;
import com.example.smartmetro.Model.User;
import com.example.smartmetro.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User has been added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user) {
        userService.updateUser(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User with ID: " + id + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("User with ID: " + id + " has been updated successfully"));
    }
    // CRUD - END

    // Getters
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(userService.getUserById(id));
    }

    @GetMapping("/get/by-email/{email}")
    public ResponseEntity getUserByEmail(@RequestBody String email) {
        return ResponseEntity.status(200).body(userService.getUserByEmail(email));
    }

    // Services
    @PutMapping("/transfer/{senderId}/{amount}")
    public ResponseEntity transferToUser(@PathVariable Integer senderId, @PathVariable Double amount, @RequestBody String email) {
        userService.transferToUser(senderId, amount, email);
        return ResponseEntity.status(200).body(new ApiResponse("Amount has been transferred successfully"));
    }
}
