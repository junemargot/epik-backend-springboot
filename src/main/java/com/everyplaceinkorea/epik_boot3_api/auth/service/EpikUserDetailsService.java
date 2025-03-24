package com.everyplaceinkorea.epik_boot3_api.auth.service;

import com.everyplaceinkorea.epik_boot3_api.auth.entity.EpikUserDetails;
import com.everyplaceinkorea.epik_boot3_api.entity.member.Member;
import com.everyplaceinkorea.epik_boot3_api.repository.Member.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EpikUserDetailsService implements UserDetailsService {

    private MemberRepository memberRepository;

    public EpikUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> memberOtional = memberRepository.findByUsername(username);
        System.out.println("확인용-"+memberOtional );
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        if (!(memberOtional.isPresent())) {
            System.out.println("비회원입니다");

        } else System.out.println("회원입니다");


        Member member = memberOtional.get();
        String role = member.getRole();
        System.out.println("role-"+role);
        //role에 따른 권한을 갖게한다.
        authorities.add(new SimpleGrantedAuthority(role));

        assert member!= null;
        return EpikUserDetails.builder()
                .id(member.getId())
                .username(username)
                .password(member.getPassword())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .authorities(authorities)
                .build();
    }
}

