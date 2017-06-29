package com.demowork.bean;

import java.util.List;

/**
 * Created by guojiadong
 * on 2017/6/29.
 */

public class JokeList {
    public List<JokeBean> data;
    @Override
    public String toString() {
        return "JokeList{" +
                "beanList=" + data +
                '}';
    }
}
