package com.kakaopay.cardPayment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * BaseResponse
 * 공통 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
@ToString
public class BaseResponse implements Serializable {
    private String code;
    private String message;
    private Object data;
    private String errorMessage;

    public BaseResponse() {
        this.setMessage("SUCCESS");
    }

    public BaseResponse(String code, String message, String errorMessage) {
        this.code = code;
        this.message = message;
        this.errorMessage = errorMessage;
    }

    public BaseResponse onSuccess(Object data) {
        this.setCode("200");
        this.setMessage("SUCCESS");
        this.setData(data);

        return this;
    }
}
