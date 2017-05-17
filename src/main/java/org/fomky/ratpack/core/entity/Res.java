package org.fomky.ratpack.core.entity;

/**
 * @author Created by Fomky on 2017/4/1316:05.
 */
public class Res {
    private Integer status = 200;
    private String message;

    public Res() {
    }

    public Res(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Res(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
