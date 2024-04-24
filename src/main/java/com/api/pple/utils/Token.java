package com.api.pple.utils;

import com.api.pple.dao.AuthDao;
import com.api.pple.exception.ClientException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Slf4j
public class Token {
    @Autowired
    static AuthDao authDao;

    public static String getAccessTokenFromHeader(HttpServletRequest servletRequest) {
        return servletRequest.getHeader("access_token");
    }

    public static void checkToken(String accessToken) {
        LocalDateTime tokenEndDate = authDao.getTokenEndDate(accessToken);

        log.info("Access Token : {} - End Date : {}", accessToken, tokenEndDate);

        if (ObjectUtils.isEmpty(tokenEndDate)) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        // token 만료 시간 확인
        if (LocalDateTime.now().isAfter(tokenEndDate)) {
            throw new ClientException(ErrorCode.EXPIRED_TOKEN);
        }
    }
}