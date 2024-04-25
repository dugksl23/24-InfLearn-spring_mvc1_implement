package com.example.study.service;


import com.example.study.Repository.MemberRepository;
import com.example.study.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberService {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    public static MemberService getInstance() {
        return new MemberService();
    }

    public Long save(Member member){
        Long saveId = memberRepository.save(member);
        return saveId;
    }

    public Member findById(Long id){
        return memberRepository.findById(id);
    }


    public List<Member> findAll(){
        return memberRepository.findAll();
    }
}
