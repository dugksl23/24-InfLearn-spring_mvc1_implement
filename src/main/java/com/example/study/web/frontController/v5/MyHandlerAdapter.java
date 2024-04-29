package com.example.study.web.frontController.v5;

import com.example.study.web.frontController.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public interface MyHandlerAdapter {

    boolean support (Object handler);
    ModelView handle(HttpServletRequest request, Object handler, Map<String, Object> model) throws ServletException, IOException;
}
