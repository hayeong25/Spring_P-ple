package com.api.pple.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SELECT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "50", "데이터 조회 실패"),
    INSERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "51", "데이터 저장 실패"),
    UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "52", "데이터 수정 실패"),
    DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "53", "데이터 삭제 실패"),
    INVALID_ID(HttpStatus.NOT_FOUND, "68", "존재하지 않는 ID입니다"),
    INVALID_ACCOUNT(HttpStatus.FORBIDDEN, "71", "탈퇴한 계정입니다"),
    INVALID_OTP(HttpStatus.FORBIDDEN, "72", "인증번호가 유효하지 않습니다"),
    INVALID_PASSWORD(HttpStatus.NOT_FOUND, "74", "비밀번호가 일치하지 않습니다"),
    DUPLICATE_ID(HttpStatus.FORBIDDEN, "88", "이미 사용 중이거나 탈퇴한 아이디입니다"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "91", "유효하지 않은 토큰입니다"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "95", "서버 오류입니다. 재시도 해주세요."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "97", "만료된 토큰입니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}