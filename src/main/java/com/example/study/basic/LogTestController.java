package com.example.study.basic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller / @ResponseBody
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring Boot";
        log.info("logTest : {}", name);
        log.trace("logTest : {}", name);
        log.debug("logTest : {}", name);
        log.warn("logTest : {}", name);
        log.error("logTest : {}", name);
        return "ok";
    }

}
