package com.example.study.web.frontController.v2;

import com.example.study.web.frontController.myView.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV2 {

    MyView process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}


