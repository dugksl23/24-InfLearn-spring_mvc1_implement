package com.example.study.dto;

import com.example.study.domain.Address;
import com.example.study.domain.Member;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberSignUpRequestDto {

    private String memberName;
    private Address address;

    public static Member createMemberEntityFrom(String memberName, Address address) {
        memberName = memberName;
        address = address;
        return Member.builder().memberName(memberName).address(address).build();

    }
}
