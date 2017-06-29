package com.demowork.bean;

/**
 * Created by guojiadong
 * on 2017/6/29.
 */

public class JokeModel {
    public int error_code;
    private String reason;
    public JokeList result;

    @Override
    public String toString() {
        return "JokeModel{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
