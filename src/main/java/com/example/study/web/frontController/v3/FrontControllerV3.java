package com.example.study.web.frontController.v3;

import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.MyView;
import com.example.study.web.frontController.v2.ControllerV2;

import com.example.study.web.frontController.v3.controller.MemberListControllerV3;
import com.example.study.web.frontController.v3.controller.MemberSignupControllerV3;
import com.example.study.web.frontController.v3.controller.MemberSignupProcControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
//@Controller
//@RequestMapping("/front-controller/v3/*")
@Slf4j
public class FrontControllerV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMapV3 = new HashMap<>();

    public FrontControllerV3() {

        String absolutePath = "/front-controller/v3/member/";
        controllerMapV3.put(absolutePath + "signup", new MemberSignupControllerV3());
        controllerMapV3.put(absolutePath + "memberList", new MemberListControllerV3());
        controllerMapV3.put(absolutePath + "signupProc", new MemberSignupProcControllerV3());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("1. Front Controller v3 Http Request 요청 들어옴");
        String requestURI = req.getRequestURI();
        //1.  컨트롤러 식별 및 호출 (다형성)
        log.info("1. 컨트롤러 식별 및 호출 (다형성)");
        ControllerV3 controllerV3 = controllerMapV3.get(requestURI);
        if (controllerV3 == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. 해당 Controller 에 request Param 데이터 넘기기.
        log.info("2. 해당 Controller 에 request Param 데이터 넘기기.");
        Map<String, String> paramMap = createParamMap(req);

        // 3. 해당 컨틀롤러에서 비지니스 로직 수행 및 논리 뷰를 dispatcher에게 modelView 반환
        //    -> 논리뷰의 이름을 생성자 주입으로 객체러 생송된 ModelView를 반환한다.
        ModelView mv = controllerV3.process(paramMap);
        //   인자로 논리뷰 이름과 requestParam (model)이 있다.
        log.info("3. 해당 컨틀롤러에서 비지니스 로직 수행 및 논리 뷰를 dispatcher에게 modelView 반환");

        // 4.viewResolver 호출하여 해당 논리뷰를 뮬리뷰 만들어주기
        MyView myView = viewResolver(mv);
        log.info("4. viewResolver 호출하여 해당 논리뷰를 뮬리뷰 만들어주기");
        // 5. render() 함수 호출, client에게 html 페이지와 모델 데이터 응답.
        myView.render(mv.getModel(), req, resp);
        log.info("5. render() 함수 호출, client에게 html 페이지와 모델 데이터 응답.");

    }


    private static MyView viewResolver(ModelView mv) {
        String viewName = "/templates/member/";
        MyView myView = new MyView(viewName + mv.getViewName() + ".html");
        return new MyView(mv   .getViewName());
    }


    public Map<String, String> createParamMap(HttpServletRequest req) {


        Map<String, String> paramMap = new HashMap<>();
        if (req.getParameterMap() != null) {
            req.getParameterNames().asIterator()
                    .forEachRemaining(param -> {
                        paramMap.put(param, req.getParameter(param));
                    });

        }
//        String memberName = req.getParameter("memberName");
//        String city = req.getParameter("city");
//        String street = req.getParameter("street");
//        String zipcode = req.getParameter("zipcode");
//
//
//        paramMap.put(memberName, "memberName");
//        paramMap.put(city, "city");
//        paramMap.put(street, "street");
//        paramMap.put(zipcode, "zipcode");
        return paramMap;
    }


}
