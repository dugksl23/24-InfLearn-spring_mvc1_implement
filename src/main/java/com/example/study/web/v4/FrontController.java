package com.example.study.web.v4;


import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.MyView;
import com.example.study.web.frontController.v3.ControllerV3;
import com.example.study.web.v4.controller.MemberListControllerV4;
import com.example.study.web.v4.controller.MemberSignupControllerV4;
import com.example.study.web.v4.controller.MemberSignupProcControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
@Slf4j
public class FrontController extends HttpServlet {

    private Map<String, ControllerV4> controllerMapV4 = new HashMap<>();

    public FrontControllerV4() {

        String absolutePath = "/front-controller/v4/member/";
        controllerMapV4.put(absolutePath + "signup", new MemberSignupControllerV4());
        controllerMapV4.put(absolutePath + "memberList", new MemberListControllerV4());
        controllerMapV4.put(absolutePath + "signupProc", new MemberSignupProcControllerV4());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("1. Front Controller v3 Http Request 요청 들어옴");
        String requestURI = req.getRequestURI();
        //1.  컨트롤러 식별 및 호출 (다형성)
        log.info("1. 컨트롤러 식별 및 호출 (다형성)");
        ControllerV4 controllerV4 = controllerMapV4.get(requestURI);
        if (controllerV4 == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. 해당 Controller 에 request Param 데이터 넘기기.
        log.info("2. 해당 Controller 에 request Param 데이터 넘기기.");
        Map<String, String> paramMap = createParamMap(req);
        //3. model 객체 생성 및 컨트롤러에 넘기기.
        Map<String, Object> model = new HashMap<>();
        log.info("model 객체 생성");

        // 4. 해당 컨틀롤러에서 비지니스 로직 수행 및 논리 뷰를 dispatcher 에게 반환
        //    -> 요청 데이터로 바인딩된 dto를 비지니스 로직 수행 후, 해당 데이터를 model 에 add한다.
        //       논리뷰 이름만 반환.
        //    -> (각각의 컨트롤러에서는 HttpServlet 종속성을 제거)
        String viewName = controllerV4.process(paramMap, model);
        log.info("4. 해당 컨틀롤러에서 비지니스 로직 수행 및 논리 뷰를 dispatcher에게 modelView 반환");

        // 5.viewResolver 호출하여 해당 논리뷰를 뮬리뷰 만들어주기
        MyView myView = viewResolver(viewName) ;
        log.info("5. viewResolver 호출하여 해당 논리뷰를 뮬리뷰 만들어주기");

        // 6. render() 함수 호출, client 에게 html 페이지와 모델 데이터 응답.
        myView.render(model, req, resp);
        log.info("6. render() 함수 호출, client에게 html 페이지와 모델 데이터 응답.");

    }


    // v3
    private static MyView viewResolver(ModelView mv) {
        String viewName = "/templates/member/";
        MyView myView = new MyView(viewName + mv.getViewName() + ".html");
        return new MyView(mv   .getViewName());
    }

    //v4
    private static MyView viewResolver(String viewName) {
        String prefix = "/templates/member/";
        String suffix = ".html";
        MyView myView = new MyView(prefix + viewName + suffix);
        return myView;
    }

    public Map<String, String> createParamMap(HttpServletRequest req) {


        Map<String, String> paramMap = new HashMap<>();
        if (req.getParameterMap() != null) {
            req.getParameterNames().asIterator()
                    .forEachRemaining(param -> {
                        paramMap.put(param, req.getParameter(param));
                    });

        }

        return paramMap;
    }


}
