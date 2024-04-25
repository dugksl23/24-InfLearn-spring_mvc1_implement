package com.example.study.web.frontController.v3;

import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.MyView;
import com.example.study.web.frontController.v2.ControllerV2;

import com.example.study.web.frontController.v3.controller.MemberListControllerV3;
import com.example.study.web.frontController.v3.controller.MemberSignupProcControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class FrontControllerV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMapV3 = new HashMap<>();

    public FrontControllerV3() {

        String absolutePath = "/front-controller/v3/member/";
        controllerMapV3.put(absolutePath + "signup", new MemberSignupProcControllerV3());
        controllerMapV3.put(absolutePath + "memberList", new MemberListControllerV3());
        controllerMapV3.put(absolutePath + "signupProc", new MemberSignupProcControllerV3());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("1. Front Controller v2 Http Request 요청 들어옴");
        String requestURI = req.getRequestURI();
        //1.  컨트롤러 식별
        log.info("2. request 에 맞는 Controller 호출");
        ControllerV3 controllerV3 = controllerMapV3.get(requestURI);
        if (controllerV3 == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 2. controller 호출 및 process() 비지니스 로직 수행 및 디스패처에게 반환
        log.info("3. 요청 controller 에서 비지니스 로직 수행 및 디스패처에게 뷰 객체를 반환");
        Map<String, String> paramMap = createParamMap(req);
        // 3.dispatcher에게 논리뷰 반환
        ModelView mv = controllerV3.process(paramMap);
        // 4.viewResolver 호출 및 뮬리뷰 만들어주기
        String viewName = "/templates/member/";
        MyView myView = new MyView(viewName + mv.getViewName());
        myView.render(req, resp);

    }


    public Map<String, String> createParamMap(HttpServletRequest req) {

        String memberName = req.getParameter("memberName");
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        String zipcode = req.getParameter("zipcode");

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(memberName, "memberName");
        paramMap.put(city, "city");
        paramMap.put(street, "street");
        paramMap.put(zipcode, "zipcode");
        return paramMap;
    }


}
