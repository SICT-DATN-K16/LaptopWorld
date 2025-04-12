package com.nah.laptopworld.dto.response;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String content) {
}
