package com.nah.laptopworld.dto.response;

import com.nah.laptopworld.service.validator.RegisterChecked;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@RegisterChecked
public class RegisterResponse {
    @Size(min = 3, message = "Họ phải có ít nhẩt 3 kí tự")
    private String firstName;
    private String lastName;
    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    private String password;
    @Size(min = 3, message = "Xác nhân mật khẩu phải có ít nhất 3 kí tự")
    private String confirmPassword;
}
