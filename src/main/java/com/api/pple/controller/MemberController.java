package com.api.pple.controller;

import com.api.pple.dto.MemberDto;
import com.api.pple.dto.PointDto;
import com.api.pple.dto.SalesDto;
import com.api.pple.service.MemberService;
import com.api.pple.utils.Token;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    /*
    * 회원가입
    */
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid MemberDto.Join request) {
        log.info("MemberController join requestBody : {}", request);
        return ResponseEntity.ok(memberService.join(request));
    }

    /*
    * 로그인
    */
    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestBody @Valid MemberDto.Login request) {
        log.info("MemberController login requestBody : {}", request);
        return ResponseEntity.ok(memberService.login(request));
    }
    
    /*
    * ID 찾기 
    */
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody @Valid MemberDto.FindId request) {
        log.info("MemberController findId requestBody : {}", request);
        return ResponseEntity.ok(memberService.findId(request));
    }
    
    /*
    * 비밀번호 찾기
    */
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestBody @Valid MemberDto.FindPw request) {
        log.info("MemberController findPassword requestBody : {}", request);
        return ResponseEntity.ok(memberService.findPw(request));
    }

    /*
    * 회원 포인트 조회 
    */
    @PostMapping("/point")
    public ResponseEntity<Integer> getMemberPoint(HttpServletRequest servletRequest) {
        log.info("MemberController getMemberPoint Header : {}", servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(memberService.getMemberPoint(accessToken));
    }

    /*
    * 회원 포인트 내역 조회 
    */
    @PostMapping("/point-history")
    public ResponseEntity<List<PointDto>> getMemberPointHistory(HttpServletRequest servletRequest) {
        log.info("MemberController getMemberPointHistory Header : {}", servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(memberService.getMemberPointHistory(accessToken));
    }

    /*
    * 정회원 월 매출, 누적 매출 조회 
    */
    @PostMapping("/month-sales")
    public ResponseEntity<SalesDto> getMemberMonthlySales(HttpServletRequest servletRequest) {
        log.info("MemberController getMemberMonthlySales Header : {}", servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(memberService.getMemberMonthlySales(accessToken));
    }

    /*
    * 회원 비밀번호 변경 
    */
    @PutMapping("/modify-password")
    public void modifyPassword(@RequestBody @Valid MemberDto.Modify request) {
        log.info("MemberController modifyPassword requestBody : {}", request);
        memberService.modifyPassword(request);
    }

    /*
    * 회원 휴대폰 번호 변경
    */
    @PutMapping("/modify-phone")
    public void modifyTelNo(@RequestBody @Valid MemberDto.Modify request, HttpServletRequest servletRequest) {
        log.info("MemberController modifyTelNo requestBody : {}, Header : {}", request, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        memberService.modifyTelNo(request, accessToken);
    }

    /*
    * 로그아웃 
    */
    @PostMapping("/logout")
    public void logout(HttpServletRequest servletRequest) {
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        memberService.logout(accessToken);
    }

    /*
    * 회원탈퇴
    */
    @PutMapping("/withdraw")
    public void withdraw(@RequestBody @Valid MemberDto.Login request, HttpServletRequest servletRequest) {
        log.info("MemberController withdraw requestBody : {}, Header : {}", request, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        memberService.withdraw(request, accessToken);
    }
}