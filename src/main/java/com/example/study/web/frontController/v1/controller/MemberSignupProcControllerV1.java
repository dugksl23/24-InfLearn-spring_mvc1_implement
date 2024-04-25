package com.example.study.web.frontController.v1.controller;

import com.example.study.web.frontController.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MemberSignupProcControllerV1 implements ControllerV1 {


    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("회원 등록 페이지 가기로 들어옴");

        String viewPath = "signup";
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req, resp);
    }
}
