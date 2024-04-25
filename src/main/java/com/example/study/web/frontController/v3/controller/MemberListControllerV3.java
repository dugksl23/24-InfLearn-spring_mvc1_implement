package com.example.study.web.frontController.v3.controller;

import com.example.study.domain.Member;
import com.example.study.service.MemberService;
import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private MemberService memberService = MemberService.getInstance();


    @Override
    public ModelView process(Map<String, String> modelAttribute) {
        //1. 컨트롤러의 비지니스 로직을 서비스에서 수행
        List<Member> members = memberService.findAll();

        //2. 논리뷰 반환 및 뷰 객체 생성하여 response 데이터 셋팅
        ModelView modelView = new ModelView("memberList");
        modelView.getModel().put("members", members);
        return modelView;
    }
}
