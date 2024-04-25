package com.example.study.web.frontController.v3.controller;

import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.v3.ControllerV3;

import java.util.Map;

public class MemberSignupProcControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> modelAttribute) {
        ModelView modelView = new ModelView("signupProc");
        return modelView;
    }
}
