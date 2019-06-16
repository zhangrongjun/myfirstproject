package com.eccom.myproject.myproject.controller;

import com.eccom.myproject.myproject.dto.AccessTokenDto;
import com.eccom.myproject.myproject.dto.GithubUser;
import com.eccom.myproject.myproject.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.redirect_url}")
    private String redirect_url;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirect_url);
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret(client_secret);

        // 获取git的 token
        String accesToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accesToken);
        System.out.println(user.toString());

        return  "index";
    }
}
