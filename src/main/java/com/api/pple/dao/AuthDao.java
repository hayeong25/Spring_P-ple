package com.api.pple.dao;

import com.api.pple.dto.KakaoDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AuthDao {
    LocalDateTime getTokenEndDate(String accessToken);

    int insertToken(String id, String accessToken);

    int deleteToken(String accessToken);

    int deleteOtp(String telNo);

    int insertOtp(String telNo, String otp);

    int insertKakaoTalkLog(KakaoDto kakaoTalk, String response);
}