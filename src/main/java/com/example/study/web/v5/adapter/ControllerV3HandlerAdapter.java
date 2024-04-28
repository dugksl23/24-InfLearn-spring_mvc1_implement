package com.example.study.web.v5.adapter;

import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.v3.ControllerV3;
import com.example.study.web.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        //구현체의 타입을 지원하는지 확인.
        return handler instanceof ControllerV3;

    }

    @Override
    public ModelView handle(HttpServletRequest request, Object handler, Map<String, Object> model) throws ServletException, IOException {
        ControllerV3 controllerV3 = (ControllerV3) handler;
        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controllerV3.process(paramMap);
        mv.setModel(model);

        return mv;
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
