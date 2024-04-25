package com.api.pple.interceptor;

import com.api.pple.utils.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {
    String[] exceptUri = {"/auth/otp", "/member/join", "/member/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("==================== START ====================");

        if (!Arrays.asList(exceptUri).contains(request.getRequestURI())) {
            String accessToken = Token.getAccessTokenFromHeader(request);
            log.info("AccessToken : {}", accessToken);
            Token.checkToken(accessToken);
        }

        return true;
    }
}