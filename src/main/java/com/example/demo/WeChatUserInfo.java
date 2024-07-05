package com.example.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeChatUserInfo {

    private String UserId;


    public WeChatUserInfo() {
    }

    public WeChatUserInfo(String UserId) {
        this.UserId = UserId;
    }
}

