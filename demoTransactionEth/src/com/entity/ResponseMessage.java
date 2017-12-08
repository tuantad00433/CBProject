package com.entity;

public class ResponseMessage {
    int code;
    String msg;
    String detai;

    public ResponseMessage(int code, String msg, String detai) {
        this.code = code;
        this.msg = msg;
        this.detai = detai;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetai() {
        return detai;
    }

    public void setDetai(String detai) {
        this.detai = detai;
    }
}
