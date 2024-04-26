package com.api.pple.service;

import com.api.pple.dao.AuthDao;
import com.api.pple.dto.request.AuthRequest;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import com.api.pple.utils.KakaoTalk;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    AuthDao authDao;

    public String sendOtp(AuthRequest request) {
        // 기존 otp DELETE
        int result = Optional.of(authDao.deleteOtp(request.getTelNo()))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }

        String otp = RandomStringUtils.randomNumeric(6);

        // 신규 otp INSERT
        result = Optional.of(authDao.insertOtp(request.getTelNo(), otp))
                         .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        String message = "[P플 본인확인] 인증번호 [" + otp + "]를 입력해 주세요.";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, request.getTelNo(), "10056");

        return otp;
    }
}