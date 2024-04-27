package com.example.study.web.frontController.v4;

import com.example.study.web.frontController.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @param "paramMap"
 * @param "model"
 * @return 논리뷰 이름 String
 */
public interface ControllerV4 {

    String process(Map<String, String> paramMap, Map<String, Object> model);

}
