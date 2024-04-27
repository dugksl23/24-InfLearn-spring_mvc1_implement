package com.example.study.web.frontController.v3;

import com.example.study.web.frontController.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> model);
}
