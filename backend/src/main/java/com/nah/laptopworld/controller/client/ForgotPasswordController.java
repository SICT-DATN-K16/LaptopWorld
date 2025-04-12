package com.nah.laptopworld.controller.client;

import com.nah.laptopworld.dto.response.MailBody;
import com.nah.laptopworld.model.ForgotPassword;
import com.nah.laptopworld.model.User;
import com.nah.laptopworld.dto.request.ResetPassword;
import com.nah.laptopworld.repository.ForgotPasswordRepository;
import com.nah.laptopworld.service.MailService;
import com.nah.laptopworld.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/forgot-password")
public class ForgotPasswordController {
    private final UserService userService;
    private final MailService mailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordController(UserService userService,
                                    MailService mailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mailService = mailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // send mail for email verification
    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
             return ResponseEntity.badRequest().body(Map.of("message", "Email là bắt buộc"));
        }
        
        User user = this.userService.getUserByEmail(email);
        if (user == null) {
             return ResponseEntity.badRequest().body(Map.of("message", "Email không tồn tại"));
        }
        
        // Xóa OTP cũ nếu có
        this.forgotPasswordRepository.deleteByUserId(user.getId());
        
        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .content("Đây là mã OTP để đặt lại mật khẩu của bạn: " + otp)
                .subject("Mã OTP đặt lại mật khẩu LaptopWorld")
                .build();
        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) // 5 phút
                .user(user)
                .build();

        try {
            mailService.sendSimpleMessage(mailBody);
            forgotPasswordRepository.save(fp);
        } catch (Exception e) {
            // Log lỗi gửi mail
            return ResponseEntity.internalServerError().body(Map.of("message", "Lỗi khi gửi email OTP."));
        }

        return ResponseEntity.ok(Map.of("message", "Mã OTP đã được gửi đến email của bạn."));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otpStr = request.get("otp");

        if (email == null || email.isEmpty() || otpStr == null || otpStr.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email và OTP là bắt buộc."));
        }

        User user = this.userService.getUserByEmail(email);
         if (user == null) {
             return ResponseEntity.badRequest().body(Map.of("message", "Email không tồn tại."));
        }

        int otp;
        try {
            otp = Integer.parseInt(otpStr);
        } catch (NumberFormatException e) {
             return ResponseEntity.badRequest().body(Map.of("message", "OTP không hợp lệ."));
        }

        ForgotPassword fp = this.forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElse(null); // Trả về null nếu không tìm thấy
                
        if (fp == null) {
             return ResponseEntity.badRequest().body(Map.of("message", "Mã OTP không chính xác."));
        }
        
        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            this.forgotPasswordRepository.deleteById(fp.getFpId()); // Sửa lại tên phương thức repo nếu cần
            return ResponseEntity.status(HttpStatus.GONE).body(Map.of("message", "Mã OTP đã hết hạn.")); // 410 Gone
        }
        
        // Không xóa OTP ở đây, để có thể dùng cho bước reset password
        // this.forgotPasswordRepository.deleteById(fp.getFpId());

        return ResponseEntity.ok(Map.of("message", "Mã OTP hợp lệ."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPassword resetPassword) {
        
        if (resetPassword.getEmail() == null || resetPassword.getOtp() == 0 ||
            resetPassword.getNewPassword() == null || resetPassword.getConfirmPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Thiếu thông tin bắt buộc."));    
        }
        
        if (!resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Mật khẩu mới và xác nhận mật khẩu không khớp."));
        }

        User user = this.userService.getUserByEmail(resetPassword.getEmail());
        if (user == null) {
             return ResponseEntity.badRequest().body(Map.of("message", "Email không tồn tại."));
        }
        
        // Xác thực lại OTP một lần nữa trước khi đổi mk
         ForgotPassword fp = this.forgotPasswordRepository.findByOtpAndUser(resetPassword.getOtp(), user)
                .orElse(null);
                
        if (fp == null) {
             return ResponseEntity.badRequest().body(Map.of("message", "Mã OTP không chính xác hoặc đã được sử dụng."));
        }
        
        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            this.forgotPasswordRepository.deleteById(fp.getFpId()); 
            return ResponseEntity.status(HttpStatus.GONE).body(Map.of("message", "Mã OTP đã hết hạn.")); 
        }

        String encodedPassword = this.passwordEncoder.encode(resetPassword.getNewPassword());
        this.userService.updatePassword(resetPassword.getEmail(), encodedPassword);
        
        // Xóa OTP sau khi đã đổi mật khẩu thành công
        this.forgotPasswordRepository.deleteById(fp.getFpId());

        return ResponseEntity.ok(Map.of("message", "Mật khẩu đã được cập nhật thành công!"));
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }

    // Endpoint này có vẻ không cần thiết, tạm comment
    // @PostMapping("/delete/{email}")
    // public ResponseEntity<Map<String, String>> deleteOTP(@PathVariable String email) {
    //     User user = this.userService.getUserByEmail(email);
    //     if (user != null) {
    //        this.forgotPasswordRepository.deleteByUserId(user.getId());
    //     }
    //     return ResponseEntity.ok(Map.of("message", "delete success!"));
    // }
}
