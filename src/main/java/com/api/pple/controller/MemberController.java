package com.api.pple.controller;

import com.api.pple.dto.request.MemberRequest;
import com.api.pple.dto.response.MemberResponse;
import com.api.pple.service.MemberService;
import com.api.pple.utils.Token;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    /*
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid MemberRequest.Join request) {
        return ResponseEntity.ok(memberService.join(request));
    }

    /*
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<MemberResponse.Login> login(@RequestBody @Valid MemberRequest.Login request) {
        return ResponseEntity.ok(memberService.login(request));
    }

    /*
     * ID 찾기
     */
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody @Valid MemberRequest.FindId request) {
        return ResponseEntity.ok(memberService.findId(request));
    }
    
    /*
     * 비밀번호 찾기
     */
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestBody @Valid MemberRequest.FindPw request) {
        return ResponseEntity.ok(memberService.findPw(request));
    }

    /*
    * 사업자 인증 (일반회원 -> 정회원)
    */
    @PutMapping("/business")
    public ResponseEntity<String> certifyBusiness(@RequestBody @Valid MemberRequest.Business request, HttpServletRequest servletRequest) {
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(memberService.certifyBusiness(request, accessToken));
    }

    /*
     * 포인트 적립, 사용
     */
    @PutMapping("/updatePoint")
    public ResponseEntity<String> updateMemberPoint(@RequestBody @Valid MemberRequest.Point request) {
        return ResponseEntity.ok(memberService.updateMemberPoint(request));
    }

    /*
     * 회원 포인트 조회
     */
    @PostMapping("/point")
    public ResponseEntity<Integer> getMemberPoint(HttpServletRequest servletRequest) {
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(memberService.getMemberPoint(accessToken));
    }

    /*
     * 회원 포인트 내역 조회
     */
    @PostMapping("/point-history")
    public ResponseEntity<List<MemberResponse.Point>> getMemberPointHistory(HttpServletRequest servletRequest) {
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(memberService.getMemberPointHistory(accessToken));
    }

    /*
     * 정회원 월 매출, 누적 매출 조회
     */
    @PostMapping("/month-sales")
    public ResponseEntity<MemberResponse.Sales> getMemberMonthlySales(HttpServletRequest servletRequest) {
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(memberService.getMemberMonthlySales(accessToken));
    }

    /*
     * 회원 비밀번호 변경
     */
    @PutMapping("/modify-password")
    public void modifyPassword(@RequestBody @Valid MemberRequest.Modify request) {
        memberService.modifyPassword(request);
    }

    /*
     * 회원 휴대폰 번호 변경
     */
    @PutMapping("/modify-phone")
    public void modifyTelNo(@RequestBody @Valid MemberRequest.Modify request, HttpServletRequest servletRequest) {
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
    public void withdraw(@RequestBody @Valid MemberRequest.Login request, HttpServletRequest servletRequest) {
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        memberService.withdraw(request, accessToken);
    }
}