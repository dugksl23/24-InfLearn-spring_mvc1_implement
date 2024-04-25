package com.example.study.web.frontController.v1.controller;

import com.example.study.domain.Address;
import com.example.study.domain.Member;
import com.example.study.dto.MemberSignUpRequestDto;
import com.example.study.service.MemberService;
import com.example.study.web.frontController.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MemberSignupControllerV1 implements ControllerV1 {

    private final MemberService memberService = MemberService.getInstance();

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("회원 가입 로직 들어옴");
        String memberName = req.getParameter("memberName");
        //int age = Integer.parseInt(req.getParameter("age"));
        //Integer.parseInt() -> String -> Int
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        String zipcode = req.getParameter("zipcode");
        Address address = new Address(street, zipcode, city);

        Member member = MemberSignUpRequestDto.createMemberEntityFrom(memberName, address);
        Long save = memberService.save(member);
        Member byId = memberService.findById(save);

        //view 에 보낼 model 객체에 데이터를 set한다.
        req.setAttribute("member", byId);

        // viewResolver의 역할 : view 에 반환할 논리뷰 이름 설정.
        String viewPath = "memberList";
        //각각의 요청마다 disPatcherServlet을 생성함.
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req,resp);

    }
}
