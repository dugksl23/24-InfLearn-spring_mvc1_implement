package com.example.study.web.frontController.v5;

import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.MyView;
import com.example.study.web.frontController.v3.controller.MemberListControllerV3;
import com.example.study.web.frontController.v3.controller.MemberSignupControllerV3;
import com.example.study.web.frontController.v3.controller.MemberSignupProcControllerV3;
import com.example.study.web.frontController.v4.controller.MemberListControllerV4;
import com.example.study.web.frontController.v4.controller.MemberSignupControllerV4;
import com.example.study.web.frontController.v4.controller.MemberSignupProcControllerV4;
import com.example.study.web.frontController.v5.adapter.ControllerV3HandlerAdapter;
import com.example.study.web.frontController.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
@Slf4j
public class FrontControllerV5 extends HttpServlet {
    /***
     * HandlerMapping
     *  adapter 패턴은
     *  - 어댑터 패턴은 기존의 인터페이스를 다른 인터페이스로 변환하는 데 사용됩니다.
     *  - 이 패턴은 호환되지 않는 인터페이스를 함께 작동할 수 있도록 도와줍니다.
     */
    private final Map<String, Object> handlerMapping = new HashMap<>();
    private final List<MyHandlerAdapter> myHandlerAdapterList = new ArrayList<>();

    public FrontControllerV5() {
        //1. ComponentScan으로 모든 컨트롤러 정보를 HandlerMapping Map에 담는다.
        initHandlerMappingMap();
        // 2. 핸들러 어탑터 목록 조회
        //    - 요청 URL 에 맞는 Controller.process(requestParam, model)
        //      을 handle 하는 HandlerAdopter를 반환.
        initHandlerAdopterList();

    }

    private void initHandlerMappingMap() {
        //v4
        handlerMapping.put("/front-controller/v5/v4/member/signup", new MemberSignupControllerV4());
        handlerMapping.put("/front-controller/v5/v4/member/memberList", new MemberListControllerV4());
        handlerMapping.put("/front-controller/v5/v4/member/signupProc", new MemberSignupProcControllerV4());

        handlerMapping.put("/front-controller/v5/v3/member/signup", new MemberSignupControllerV3());
        handlerMapping.put("/front-controller/v5/v3/member/memberList", new MemberListControllerV3());
        handlerMapping.put("/front-controller/v5/v3/member/signupProc", new MemberSignupProcControllerV3());
    }

    private void initHandlerAdopterList() {
        myHandlerAdapterList.add(new ControllerV4HandlerAdapter());
        myHandlerAdapterList.add(new ControllerV3HandlerAdapter());
    }

    @Override

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 1. HandlerMapping 정보를 조회.
         * RequestURL과 매핑할 수 있는 구현체 class ex) MemberSignupControllerV4 가 있는 지확인
         * 있으면 해당 구현체를 객체로 생성 및 반환하고, Object class로 받아준다.(다형성)
         * ex) /front-controller/v4/member/signup
         */
        log.info("1. 핸들러 매핑 정보 조회를 위한 GetHandler() 실행");
        Object handler = getHandler(req, resp);
        log.info("getHandler() 의 구현체 반환 object :, {}", handler.getClass().getName());
        // -> MemberSignupControllerV4 controller = new MemberSignupControllerV4()

        /** 2. getHandlerAdapter(object) 의 타입 조회 support() 함수
         *  @handler - Object(구현체 class)
         *
         * @return 구현체 class 의 handlerAdapter를 반환
         * ControllerV4.process(handler) 실행할 구현체 클래스의 컨트롤러 어답터 반환
         * */
        log.info("2. 핸들러 어답터 목록 조회를 위한 getHandlerAdapter(object/Controller 구현체)");
        MyHandlerAdapter handlerAdapter = getHandlerAdapter(handler);


        /**
         * 4. 구현체 controller인 인터페이스의 ControllerV4.process(Model, requestParam) 을 실행시킬
         * handlerAdapter 의 인터페이스(다형성)을 통해 handle(requestParam, model) 함수 실행 논리뷰 이름 반환.
         */
        Map<String, Object> model = new HashMap<>();
        log.info("3. model 객체 생성 및 handlerAdapter 의 handle(model, req, resp, handler) 함수 실행");
        log.info("3-1. handlerAdapter : 구현체 컨트롤러의 인터페이스(ControllerV4)의 핸들러 어탑터의 인터페이스");
        log.info("3-2. object : 구현체 컨트롤러(memberSignupProcV4)");
        ModelView modelAndView = handlerAdapter.handle(req, handler, model);
        String viewName = modelAndView.getViewName();
        log.info("5. 구현체 컨트롤러에서 handler에게 논리뷰 String 반환");


        //4. viewResolver 호출 후 물리뷰 만들고, 뷰 객체 반환
        MyView myView = viewResolver(viewName);
        log.info("6. viewResolver 호출 및 myview 객체 생성");

        //5. 뷰객체에 model을 담고 render() 호출
        // -> html　페이지를 렌더하고 client에게 반환
        log.info("7. my view 반환 및 render() 실행");
        myView.render(req, resp, modelAndView.getModel());

    }


    public Object getHandler(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        log.info("1-1. request uri: {}", uri);
        Object handler = handlerMapping.get(uri);
        log.info("1-2. HandlerAdapterMappingMap 에서 매핑된 구현체 Controller : {}", handler.getClass().getName());

        if (handler == null) {
            response.setStatus(404);
        }
        return handler;
    }


    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        /**
         * 3. 해당 구현체 클래스들이 Interface Controller ex) ControllerV4와
         * 일치하는지를 HandlerAdapter.support(memberSignUpController) 통해 확인
         * @return true/false
         * true 일경우, HandlerAdapter의 인터페이스를 (V4 Adapter의 구현 class 의 interface) 로 반환받는다.
         */

        log.info("2-1. HandlerAdapter.support(object/구현체 클래스) 함수를 실행 ");
        log.info("2-2. support() 함수를 통한 요청 요청 구현 클래스와 부모 인터페이스 일치 여부 확인 중");
//        myHandlerAdapterList.stream().filter(handlerAdapter -> handlerAdapter.support(handler)).findFirst();
        for (MyHandlerAdapter handlerAdapter : myHandlerAdapterList) {
            if (handlerAdapter.support(handler)) {
                log.info("2-3. ControllerV4(인터페이스) 일치 여부, {}", handlerAdapter.support(handler));
                log.info("2-4. ControllerV4Handler(구현체)의 참조값을 가진, MyHandlerAdapter(핸들러 어답터 인터페이스/구현체의 참조값을 가짐) 반환 :, {}",  handlerAdapter.getClass().getName());
                return handlerAdapter;

            }
        }
        throw new IllegalArgumentException("handlerAdapter not support " + handler);
    }


    //v4
    private static MyView viewResolver(String viewName) {
        String prefix = "/templates/member/";
        String suffix = ".html";
        MyView myView = new MyView(prefix + viewName + suffix);
        return myView;

    }


}


