package com.swithun.backend.config.uniform_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedResponsor {
    /**
     * success or not
     */
    private Boolean succ;
    /**
     * the time stamp
     */
    private Long ts = System.currentTimeMillis();
    /**
     * the successful data
     */
    private Object data;
    /**
     * error code
     */
    private String code;
    /**
     * error message
     */
    private String msg;

    /**
     * return succesful Result without data
     */
    public static UnifiedResponsor ofSuccess(CommonStatusCodeEnum ase) {
        UnifiedResponsor answerEntity = new UnifiedResponsor();
        answerEntity.succ = true;
        answerEntity.code = ase.getCode();
        answerEntity.msg = ase.getMsg();
        return answerEntity;
    }

    /**
     * return fail Result without data
     */
    public static UnifiedResponsor ofFail(CommonStatusCodeEnum ase) {
        UnifiedResponsor answerEntity = new UnifiedResponsor();
        answerEntity.succ = true;
        answerEntity.code = ase.getCode();
        answerEntity.msg = ase.getMsg();
        return answerEntity;
    }

    /**
     * return succesful Result with data
     */
    public static UnifiedResponsor ofSuccess(CommonStatusCodeEnum ase, Object o) {
        UnifiedResponsor answerEntity = new UnifiedResponsor();
        answerEntity.succ = true;
        answerEntity.code = ase.getCode();
        answerEntity.msg = ase.getMsg();
        answerEntity.data = o;
        return answerEntity;
    }

    /**
     * return fail Result with data
     */
    public static UnifiedResponsor ofFail(CommonStatusCodeEnum ase, Object o) {
        UnifiedResponsor answerEntity = new UnifiedResponsor();
        answerEntity.succ = true;
        answerEntity.code = ase.getCode();
        answerEntity.msg = ase.getMsg();
        answerEntity.data = o;
        return answerEntity;
    }

    public Boolean getSucc() {
        return succ;
    }

    public void setSucc(Boolean succ) {
        this.succ = succ;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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
