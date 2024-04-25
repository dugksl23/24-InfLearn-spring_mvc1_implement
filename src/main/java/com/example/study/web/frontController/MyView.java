package com.example.study.web.frontController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//viewResolver
public class MyView {

    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    // view 객체를 생성하여 렌더링하는 디스패처에 반환하는 메서드
    public MyView render(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);

        return new MyView(viewPath);

    }
}
