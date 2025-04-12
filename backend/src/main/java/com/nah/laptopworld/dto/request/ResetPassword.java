package com.nah.laptopworld.dto.request;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResetPassword{
    String email;
    int otp;
    String newPassword;
    String confirmPassword;
}
