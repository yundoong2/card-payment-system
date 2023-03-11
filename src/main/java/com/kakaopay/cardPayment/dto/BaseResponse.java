package com.kakaopay.cardPayment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseResponse implements Serializable {
    private String code;
    private String message;
    private Object data;

    public BaseResponse() {
        this.setMessage("SUCCESS");
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse onSuccess(Object data) {
        this.setCode("200");
        this.setMessage("SUCCESS");
        this.setData(data);

        return this;
    }
}
