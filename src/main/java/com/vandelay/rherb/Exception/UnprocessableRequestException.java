package com.vandelay.rherb.Exception;

public class UnprocessableRequestException extends RuntimeException  {
    public UnprocessableRequestException(String msg) {
        super(msg);
    }
}
