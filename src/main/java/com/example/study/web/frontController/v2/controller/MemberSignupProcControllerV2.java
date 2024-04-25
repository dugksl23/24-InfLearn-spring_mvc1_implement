package com.example.study.web.frontController.v2.controller;

import com.example.study.web.frontController.myView.MyView;
import com.example.study.web.frontController.v2.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSignupProcControllerV2 implements ControllerV2 {

    @Override
    public MyView process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //프론트 컨트롤러에서 마이뷰를 호출해서 viewPath를 넘기기에
        //각각의 컨트롤러에서 해당 작업까지 마무리해서 frontController에 반환한다.
        MyView myView = new MyView("/signupProc");

        return myView;


    }
}
