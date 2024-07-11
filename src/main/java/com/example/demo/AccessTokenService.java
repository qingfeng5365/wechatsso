package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccessTokenService {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable("accesstoken")
    public String getAccessToken() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + weChatConfig.getCorpId()
                + "&corpsecret=" + weChatConfig.getSecret();

        AccessTokenResponse response = restTemplate.getForObject(url, AccessTokenResponse.class);
        //System.out.println(response.getErrcode());
        //System.out.println(response.getErrmsg());
        //System.out.println(response.getAccess_token());
        return response.getAccess_token();
    }

}

