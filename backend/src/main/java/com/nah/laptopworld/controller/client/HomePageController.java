package com.nah.laptopworld.controller.client;

import com.nah.laptopworld.dto.response.RegisterResponse;
import com.nah.laptopworld.model.LaptopModel;
import com.nah.laptopworld.model.Order;
import com.nah.laptopworld.model.User;
import com.nah.laptopworld.dto.request.ResetPassword;
import com.nah.laptopworld.service.OrderService;
import com.nah.laptopworld.service.ProductService;
import com.nah.laptopworld.service.UploadService;
import com.nah.laptopworld.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final UploadService uploadService;

    // @GetMapping("/api/products")
    // public ResponseEntity<List<LaptopModel>> getAllProducts() {
    // Pageable pageable = PageRequest.of(0, 10);
    // Page<LaptopModel> products = this.productService.fetchProducts(pageable);
    // List<LaptopModel> laptopModelList = new ArrayList<>(products.getContent());
    // // Convert to modifiable list
    //
    // // Sắp xếp sản phẩm theo rate theo thứ tự giảm dần
    // laptopModelList.sort((p1, p2) -> {
    // double avgRate1 = this.productService.getAvgRate(p1);
    // double avgRate2 = this.productService.getAvgRate(p2);
    // return Double.compare(avgRate2, avgRate1);
    // });
    //
    // return ResponseEntity.ok(laptopModelList);
    // }

    @GetMapping("/products/top-rate")
    public ResponseEntity<List<LaptopModel>> getTopRateProductApi() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<LaptopModel> products = this.productService.fetchProducts(pageable);
        List<LaptopModel> laptopModelList = new ArrayList<>(products.getContent());

        laptopModelList.sort((p1, p2) -> {
            double avgRate1 = this.productService.getAvgRate(p1);
            double avgRate2 = this.productService.getAvgRate(p2);
            return Double.compare(avgRate2, avgRate1);
        });
        return ResponseEntity.ok(laptopModelList);
    }

    @GetMapping("/user/orders")
    public ResponseEntity<List<Order>> getUserOrders(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User currentUser = new User();
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<Order> orders = this.orderService.fetchOrdersByUser(currentUser);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/profile")
    public ResponseEntity<User> getUserProfile(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        long id = (long) session.getAttribute("id");
        User user = this.userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/profile")
    public ResponseEntity<User> updateUserProfile(
            @RequestBody User user,
            @RequestParam(value = "avatar", required = false) MultipartFile file,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        long id = (long) session.getAttribute("id");
        User currentUser = this.userService.getUserById(id);

        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (file != null && !file.isEmpty()) {
            String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
            currentUser.setAvatar(avatar);
        }

        currentUser.setDob(user.getDob());
        currentUser.setFullName(user.getFullName());
        currentUser.setAddress(user.getAddress());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setGender(user.getGender());

        User updatedUser = this.userService.handleSaveUser(currentUser);
        return ResponseEntity.ok(updatedUser);
    }

    // @PostMapping("/forgot-password/verify-email")
    // public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String>
    // request) {
    // String email = request.get("email");
    // User user = this.userService.getUserByEmail(email);
    //
    // if (user == null) {
    // Map<String, String> response = new HashMap<>();
    // response.put("message", "Email không tồn tại");
    // return ResponseEntity.badRequest().body(response);
    // }
    //
    // // Implement email verification logic
    // Map<String, String> response = new HashMap<>();
    // response.put("message", "Mã xác nhận đã được gửi về email của bạn");
    // return ResponseEntity.ok(response);
    // }
    //
    // @PostMapping("/forgot-password/verify-otp")
    // public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request)
    // {
    // String email = request.get("email");
    // String otp = request.get("otp");
    //
    // // Implement OTP verification logic
    // Map<String, String> response = new HashMap<>();
    // response.put("message", "Mã xác nhận hợp lệ");
    // return ResponseEntity.ok(response);
    // }
    //
    // @PostMapping("/forgot-password/reset-password")
    // public ResponseEntity<?> resetPassword(@RequestBody ResetPassword
    // resetPassword) {
    // User user = this.userService.getUserByEmail(resetPassword.getEmail());
    //
    // if (user == null) {
    // Map<String, String> response = new HashMap<>();
    // response.put("message", "Email không tồn tại");
    // return ResponseEntity.badRequest().body(response);
    // }
    //
    // String hashPassword =
    // this.passwordEncoder.encode(resetPassword.getNewPassword());
    // this.userService.updatePassword(resetPassword.getEmail(), hashPassword);
    //
    // Map<String, String> response = new HashMap<>();
    // response.put("message", "Mật khẩu đã được cập nhật");
    // return ResponseEntity.ok(response);
    // }
}
