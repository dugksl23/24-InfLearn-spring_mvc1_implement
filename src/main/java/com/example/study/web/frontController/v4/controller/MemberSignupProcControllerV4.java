package com.example.study.web.frontController.v4.controller;

import com.example.study.web.frontController.v4.ControllerV4;

import java.util.Map;

public class MemberSignupProcControllerV4 implements ControllerV4 {
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        //request 된 object를 @ModelAttribute 객체와 key, value 값으로 바인딩된 객체를
        //model 객체에 저장하는 것과 같은 코드
        model.put("paramMap", paramMap);

        return "signup";
    }
}
