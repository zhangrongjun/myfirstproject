package com.eccom.myproject.myproject.provider;

import com.alibaba.fastjson.JSON;
import com.eccom.myproject.myproject.dto.AccessTokenDto;
import com.eccom.myproject.myproject.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");
        String url="https://github.com/login/oauth/access_token";

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String str = response.body().string();

                String[] strs = str.split("&");
                String token = strs[0].split("=")[1];

                System.out.println(token);
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
    }


    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        String url="https://api.github.com/user?access_token=";
        Request request = new Request.Builder()
                .url(url+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String  str =response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return  githubUser;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
