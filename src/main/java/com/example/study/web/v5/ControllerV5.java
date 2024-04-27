package com.example.study.web.v5;

import java.util.Map;

public interface ControllerV5 {

    String process(Map<String, String> requestParamMap, Map<String, Object> model);
}
