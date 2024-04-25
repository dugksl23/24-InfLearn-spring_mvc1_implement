package com.example.study.web.frontController.v2.controller;

import com.example.study.domain.Address;
import com.example.study.domain.Member;
import com.example.study.dto.MemberSignUpRequestDto;
import com.example.study.service.MemberService;
import com.example.study.web.frontController.MyView;
import com.example.study.web.frontController.v2.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MemberSignupControllerV2 implements ControllerV2 {

    private MemberService memberService = MemberService.getInstance();
    @Override
    public MyView process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //프론트 컨트롤러에서 마이뷰를 호출해서 viewPath를 넘기기에
        //각각의 컨트롤러에서 해당 작업까지 마무리해서 frontController에 반환한다.
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
        MyView myView = new MyView("/signupProc");

        return myView;


    }
}
