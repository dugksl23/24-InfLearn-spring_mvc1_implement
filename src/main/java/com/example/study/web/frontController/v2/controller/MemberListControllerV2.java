package com.example.study.web.frontController.v2.controller;

import com.example.study.domain.Member;
import com.example.study.service.MemberService;
import com.example.study.web.frontController.myView.MyView;
import com.example.study.web.frontController.v2.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MemberListControllerV2 implements ControllerV2 {

    private MemberService memberService = MemberService.getInstance();

    @Override
    public MyView process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Member> all = memberService.findAll();
        req.setAttribute("members", all);

        // 각각의 controller의 return 위치
        return new MyView("/memberList");
    }
}