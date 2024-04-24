package com.api.pple.service;

import com.api.pple.dao.AuthDao;
import com.api.pple.dto.AuthDto;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import com.api.pple.utils.KakaoTalk;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    @Autowired
    AuthDao authDao;

    public String sendOtp(AuthDto.Otp request) {
        // 기존 otp DELETE
        int result = authDao.deleteOtp(request.getTelNo());

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }

        String otp = RandomStringUtils.randomNumeric(6);

        // 신규 otp INSERT
        result = authDao.insertOtp(request.getTelNo(), otp);

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        String message = "[P플 본인확인] 인증번호 [" + otp + "]를 입력해 주세요.";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, request.getTelNo(), "10056");

        return otp;
    }
}