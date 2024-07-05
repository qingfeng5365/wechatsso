package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WeChatController {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/api/wxlogin")
    public RedirectView wechatLogin() {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + weChatConfig.getCorpId()
                + "&redirect_uri=" + weChatConfig.getRedirectUri()
                + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        return new RedirectView(url);
    }

    @GetMapping("/api/auth/callback")
    public String wechatCallback(@RequestParam("code") String code) {
        
        String access_token = getAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=" + access_token
                + "&code=" + code;

        String user_id = getUserId(url,access_token);
        
        String word_code = getWorkCode(user_id,access_token);

        return "redirect:https://xxxxxx.cn/wxlogin?id="+word_code;

    }

    private String getAccessToken() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + weChatConfig.getCorpId()
                + "&corpsecret=" + weChatConfig.getSecret();
       
        AccessTokenResponse response = restTemplate.getForObject(url, AccessTokenResponse.class);
       
        return response.getAccess_token();
    }

    private String getUserId(String url, String access_token) {

        String responseObject = restTemplate.getForObject(url, String.class);    

        String regex = "\"UserId\"\\s*:\\s*\"([a-zA-Z0-9]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(responseObject);
 
        String userId = "";
      
        if (matcher.find()) {
            userId = matcher.group(1);
            
        } else {
            System.out.println("未找到 UserID");
        } 

        return userId;

    }

    private String getWorkCode(String user_id, String access_token) {

        
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+access_token+"&userid="+user_id;

        String responseObject = restTemplate.getForObject(url, String.class);

        String regex = "\"name\":\"工号\",\"value\":\"(\\d+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(responseObject);
 
        String work_code = "";
      
        if (matcher.find()) {
            work_code = matcher.group(1);
            
        } else {
            System.out.println("未找到 UserID");
        } 

        return work_code;

    }

}

