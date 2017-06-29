package com.demowork.bean;

/**
 * Created by guojiadong
 * on 2017/6/29.
 */

public class JokeBean {
    public String content;
    public String updatetime;
    public String url;

    @Override
    public String toString() {
        return "JokeBean{" +
                "content='" + content + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
