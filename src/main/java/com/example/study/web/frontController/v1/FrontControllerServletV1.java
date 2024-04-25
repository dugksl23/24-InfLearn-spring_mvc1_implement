package com.example.study.web.frontController.v1;

import com.example.study.web.frontController.v1.controller.MemberListControllerV1;
import com.example.study.web.frontController.v1.controller.MemberSignupProcControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
// 1. /*는 /v1 밑에 어떤 경로로 들어오든 FrontControllerServletV1(서블렛)이 호출되게끔 되어있다.
public class FrontControllerServletV1 extends HttpServlet {

    //2. 어떤 요청 컨트롤러로 들어왔는지 URL을 통해 식별
    private Map<String, ControllerV1> controllerMapV1 = new HashMap<>();

    public FrontControllerServletV1() {
        //3. 생성자 주입을 통한 객체 생성 시점에
        //   3-1. 하위 컨트롤러 서블렛의 모든 URL을 등록
        //   3-2. 해당 컨트롤러를 호출 및 반환

        String absolutePath = "/front-controller/v1/member/";
        controllerMapV1.put(absolutePath + "signup", new MemberSignupProcControllerV1());
        controllerMapV1.put(absolutePath + "memberList", new MemberListControllerV1());
        controllerMapV1.put(absolutePath + "signupProc", new MemberSignupProcControllerV1());

    }

    @Override
    // 요청 controller를 식별하기 위한 DispatcherServlet(FrontControlle）의 비지니스 로직을 수행할 @Service 영역이다.
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        //다형성을 통해 인터페이스로 요청에 따른 Controller를 식별
        //ControllerV1 controllerV1 = new new MemberSignupProcControllerV1();
        ControllerV1 controllerV1 = controllerMapV1.get(requestURI);
        if(controllerV1 == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        log.info("request controller : {} ", controllerV1.toString());
        // controller 호출
        controllerV1.process(req, resp);
    }
}
