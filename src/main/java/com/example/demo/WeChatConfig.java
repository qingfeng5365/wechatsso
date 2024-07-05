package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeChatConfig {

    @Value("${wechat.corpId}")
    private String corpId;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${wechat.agentId}")
    private String agentId;

    @Value("${wechat.redirectUri}")
    private String redirectUri;

    // Getters
    public String getCorpId() { return corpId; }
    public String getSecret() { return secret; }
    public String getAgentId() { return agentId; }
    public String getRedirectUri() { return redirectUri; }
}

