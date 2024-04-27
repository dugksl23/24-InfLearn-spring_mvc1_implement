package com.example.study.web.frontController.v4.controller;

import com.example.study.domain.Address;
import com.example.study.domain.Member;
import com.example.study.dto.MemberSignUpRequestDto;
import com.example.study.service.MemberService;
import com.example.study.web.frontController.v4.ControllerV4;

import java.util.Map;

public class MemberSignupControllerV4 implements ControllerV4 {

    private MemberService memberService = MemberService.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        // json 오브젝트에 데이터가 리스트로 담겨져 있기에
        // 받는 곳에서는 인터페이스를 통해 <String, object>로 받고
        // 그것을 상속하는 곳에서 타입을 특정하면 된다.

        Member member = null;

        if (!paramMap.isEmpty()) {
            String memberName = paramMap.get("memberName");
            String city = paramMap.get("city");
            String zipcode = paramMap.get("zipcode");
            String street = paramMap.get("street");
            Address address = new Address(street, city, zipcode);

            MemberSignUpRequestDto.createMemberEntityFrom(memberName, address);
            Long save = memberService.save(member);
            member = memberService.findById(save);
        }

        //web 계층에서 전달된 파라미터들을 model 객체에 담아서 보낸다.
        model.put("member", member);

        return "memberList";

    }
}
