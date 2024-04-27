package com.example.study.web.frontController.v4.controller;

import com.example.study.domain.Member;
import com.example.study.service.MemberService;
import com.example.study.web.frontController.v4.ControllerV4;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    private MemberService memberService = MemberService.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        //1. 컨트롤러의 비지니스 로직을 서비스에서 수행
        List<Member> members = memberService.findAll();

        //2. 논리뷰 반환 및 프론트 컨트롤러에서 넘어온 Model 객체에
        //   비지니스 로직을 수행한 값을 add 한다.
        model.put("members", members);

        return "memberList";
    }
}
