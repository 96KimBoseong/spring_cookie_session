package com.cookie_session.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        System.out.println("value="+value);
        return "get-cookie : " + value;
    }

    @GetMapping("/create-session")
    public String CreateSession(HttpServletRequest req){
        HttpSession session = req.getSession(true);
        //세션이 존재할 경우 세션 반환 없을 경우 새로운 세션을 생성 후 반환
        session.setAttribute(AUTHORIZATION_HEADER,"kim bo sung");
        // 세션에 저장될 정보 name-value 추가
        return "create-session";
    }
    @GetMapping("/get-session")
    public String getSession(HttpServletRequest req){
        HttpSession session = req.getSession(false);

        String value = (String) session.getAttribute(AUTHORIZATION_HEADER);
        System.out.println("value="+value);
        return "get-session : " + value;
    }

    public static void addCookie(
            String cookieValue, HttpServletResponse res
    ){
        cookieValue = URLEncoder.encode(cookieValue, StandardCharsets.UTF_8).replaceAll("\\+", "%20");//cookie value는 공백이 불가하다


        Cookie cookie = new Cookie(AUTHORIZATION_HEADER,cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(30*60);

        res.addCookie(cookie);
    }

}
