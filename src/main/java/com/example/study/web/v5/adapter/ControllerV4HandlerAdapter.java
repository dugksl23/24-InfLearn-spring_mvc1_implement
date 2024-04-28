package com.example.study.web.v5.adapter;

import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.v4.ControllerV4;
import com.example.study.web.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

    private boolean support;

    @Override
    public boolean support(Object handler) {
        this.support = (handler instanceof ControllerV4);
        // instance -> 객체 class 혹은 interface 타입 확인하는 키워드
        // 1.1 class instance class
        // 1.2 class instance interface
        return this.support;
    }

    @Override
    public ModelView handle(HttpServletRequest request, Object handler, Map<String, Object> model) throws ServletException, IOException {

        log.info("4. 핸들러 어탑터(인터페이스)의 구현체 호출");
        String viewName = "";
        if (this.support) {
            log.info("4.0. object(memberSignUpProc 구현체를 object 에서 -> ControllerV4 다운캐스팅");
            ControllerV4 controller4 = (ControllerV4) handler;
            log.info("4-1. request 에서 paramMap 생성");
            Map<String, String> paramMap = createParamMap(request);
            log.info("4-2. ControllerV4 인터페이스에(참조값 : memberSignupProc) 를 process(paramMap과 model을 매개변수로 전달) 실행");
            viewName = controller4.process(paramMap, model);
            log.info("4-3. 해당 구현체 클래스 비지니스 로직 수행 후 viewName 전달");

        }

        // interface의 공통 규격의 반환타입이 ModelAndView이다. 따라서
        // v4의 Controller Interface의 어답터에서 process를 진행하고
        // 반환타입이 String 이기에, adapter에서 ModelAndView를 생성하고 적용시켜 반환.
        ModelView modelView = new ModelView(viewName);
        modelView.setModel(model);

        return modelView;


    }

    private Map<String, String> createParamMap(HttpServletRequest req) {


        Map<String, String> paramMap = new HashMap<>();
        if (req.getParameterMap() != null) {
            req.getParameterNames().asIterator().forEachRemaining(param -> {
                paramMap.put(param, req.getParameter(param));
            });

        }

        return paramMap;
    }

}
