package com.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author: laizc
 * @date: created in 2023/6/8
 * @desc:
 **/
@RestController
public class CookieAndSessionController {

    @GetMapping("/cookie")
    public String cookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        Enumeration<String> sessionList = session.getAttributeNames();
        Cookie cookie = new Cookie("name","jeremy");
        response.addCookie(cookie);
        return "ok";
    }

}
