package com.swithun.backend.config.uniform_response;

public enum CommonStatusCodeEnum {
    SUCCESS_COMMON("200", "请求成功"),
    FAIL_COMMON("500", "请求失败");

    private String code;
    private String msg;

    private CommonStatusCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
