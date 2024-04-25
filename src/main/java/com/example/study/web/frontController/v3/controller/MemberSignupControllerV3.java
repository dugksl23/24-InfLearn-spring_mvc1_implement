package com.example.study.web.frontController.v3.controller;

import com.example.study.domain.Address;
import com.example.study.domain.Member;
import com.example.study.dto.MemberSignUpRequestDto;
import com.example.study.service.MemberService;
import com.example.study.web.frontController.ModelView;
import com.example.study.web.frontController.v3.ControllerV3;

import java.util.Map;

public class MemberSignupControllerV3 implements ControllerV3 {

    private MemberService memberService = MemberService.getInstance();

    @Override
    public ModelView process(Map<String, String> modelAttribute) {
        // json 오브젝트에 데이터가 리스트로 담겨져 있기에
        // 받는 곳에서는 인터페이스를 통해 <String, object>로 받고
        // 그것을 상속하는 곳에서 타입을 특정하면 된다.

        String memberName = modelAttribute.get("memberName");
        String city = modelAttribute.get("city");
        String zipcode = modelAttribute.get("zipcode");
        String street = modelAttribute.get("street");
        Address address = new Address(street, city, zipcode);

        Member member = MemberSignUpRequestDto.createMemberEntityFrom(memberName, address);
        Long save = memberService.save(member);
        Member byId = memberService.findById(save);


        ModelView modelView = new ModelView("signup");
        //web 계층에서 전달된 파라미터들을 model 객체에 담아서 보낸다.
        Map<String, Object> model = modelView.getModel();
        model.put("member", member);
        return modelView;
    }
}
