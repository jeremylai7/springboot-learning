package com.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public String cookie(String a, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] requestCookies = request.getCookies();
        if (a != null) {
            Cookie cookie = new Cookie("name",a);
            response.addCookie(cookie);
        }
        return "ok";
    }

    @GetMapping("/session")
    @ResponseBody
    public String session(String a, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Boolean login =(Boolean) session.getAttribute("login");
        String loginInfo;
        if (login == null) {
            loginInfo = "未登录";
        } else {
            loginInfo = "已登录";
        }
        return "session id ：" + session.getId() + ":" + loginInfo;
    }

    @GetMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("login",true);
        return "ok";
    }



}
