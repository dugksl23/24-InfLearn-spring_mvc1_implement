package com.example.study.web.frontController.v2;

import com.example.study.web.frontController.MyView;
import com.example.study.web.frontController.v2.controller.MemberListControllerV2;
import com.example.study.web.frontController.v2.controller.MemberSignupProcControllerV2;
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
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMapV2 = new HashMap<>();


    public FrontControllerV2() {

        String absolutePath = "/front-controller/v2/member/";
        controllerMapV2.put(absolutePath + "signup", new MemberSignupProcControllerV2());
        controllerMapV2.put(absolutePath + "memberList", new MemberListControllerV2());
        controllerMapV2.put(absolutePath + "signupProc", new MemberSignupProcControllerV2());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("1. Front Controller v2 Http Request 요청 들어옴");
        String requestURI = req.getRequestURI();
        //1.  컨트롤러 식별
        log.info("2. request 에 맞는 Controller 호출");
        ControllerV2 controllerV2 = controllerMapV2.get(requestURI);
        if(controllerV2 == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 2. controller 호출 및 process() 비지니스 로직 수행 및 디스패처에게 반환
        log.info("3. 요청 controller 에서 비지니스 로직 수행 및 디스패처에게 뷰 객체를 반환");
        MyView view = controllerV2.process(req, resp);
        // 3.dispatcher에게 논리뷰 반환
        log.info("4. 디스패처 서블릿은 마이뷰 객체에서 렌더 함수 호출 및" +
                "5. 논리뷰 이름으로 Client 에게 jsp 포워드");
        view.render(req, resp);
    }
}
