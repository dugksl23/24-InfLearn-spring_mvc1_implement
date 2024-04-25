package com.example.study.Repository;

import com.example.study.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRepository {

    private static Map<Long, Member> memberRepository = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    public static MemberRepository getInstance() {
        return new MemberRepository();
    }

    public Long save(Member member) {

        if (member.getId() == null) {
            long andIncrement = sequence.getAndIncrement();
            member.setId(andIncrement);
        }

        memberRepository.put(member.getId(), member);
        return member.getId();
    }

    public Member findById(Long id) {
        return memberRepository.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(memberRepository.values());
    }

}
