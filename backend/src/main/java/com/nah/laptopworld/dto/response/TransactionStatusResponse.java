package com.nah.laptopworld.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransactionStatusResponse implements Serializable {
    private String status;
    private String message;
    private String data;
}
