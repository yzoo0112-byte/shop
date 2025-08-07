package com.shop.service;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember (){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test1@test.com");
        memberFormDto.setName("test");
        memberFormDto.setPassword("test");
        memberFormDto.setAddress("창원시 의창구 사림동");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    void saveMember() {
        Member member = createMember();//저장된 멤버
        Member savedMember = memberService.saveMember(member);//저장하려고 만든 멤버

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());

        System.out.println(savedMember);
    }

    @Test
    @DisplayName("중복회원 가입 테스트")
    public void saveDuplicate() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalArgumentException.class, () -> memberService.saveMember(member2));

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}