package com.nah.laptopworld.controller.admin;

import com.nah.laptopworld.model.User;
import com.nah.laptopworld.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final HttpServletRequest request;

    public UserController(UserService userService,
            UploadService uploadService,
            PasswordEncoder passwordEncoder,
            MailService mailService,
            HttpServletRequest request) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.request = request;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "roleDes", required = false) String roleDes) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<User> users = this.userService.getAllUser(fullName, roleDes, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("users", users.getContent());
        response.put("currentPage", page);
        response.put("totalPages", users.getTotalPages());
        response.put("totalItems", users.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = this.userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user,
            @RequestParam(value = "avatar", required = false) MultipartFile file) {

        if (file != null && !file.isEmpty()) {
            String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
            user.setAvatar(avatar);
        }

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));
        User createdUser = this.userService.handleSaveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id,
            @RequestBody User user,
            @RequestParam(value = "avatar", required = false) MultipartFile file) {

        User currentUser = this.userService.getUserById(id);
        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (file != null && !file.isEmpty()) {
            String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
            currentUser.setAvatar(avatar);
        }

        currentUser.setFullName(user.getFullName());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setRole(this.userService.getRoleByName(user.getRole().getName()));

        User updatedUser = this.userService.handleSaveUser(currentUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        User user = this.userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        this.userService.deleteAUser(id);
        return ResponseEntity.noContent().build();
    }
}
