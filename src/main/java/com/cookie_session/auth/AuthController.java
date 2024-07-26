package com.cookie_session.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class AuthController {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @GetMapping("/create-cookie")
    public String CreateCookie(
            HttpServletResponse res
    ){
        addCookie("KBS",res);
        return "create-cookie";
    }

    @GetMapping("/get-cookie")
    public String getCookie(@CookieValue (AUTHORIZATION_HEADER) String value){
        System.out.println("value:"+value);
        return "get-cookie" + value;
    }



    public static void addCookie(
            String cookieValue, HttpServletResponse res
    ){
        try {
            cookieValue = URLEncoder.encode(cookieValue,"utf-8").replaceAll("\\+", "%20");//cookie value는 공백이 불가하다


            Cookie cookie = new Cookie(AUTHORIZATION_HEADER,cookieValue);
            cookie.setPath("/");
            cookie.setMaxAge(30*60);

            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
