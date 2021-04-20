package com.easymall.exception;

//消息提示
public class MsgException extends RuntimeException {
    public MsgException(){
    }
    public MsgException(String msg) {
        super(msg);
    }
}
