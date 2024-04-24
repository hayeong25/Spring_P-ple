package com.api.pple.utils;

import com.api.pple.dao.AuthDao;
import com.api.pple.dto.KakaoDto;
import com.api.pple.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class KakaoTalk {
    @Autowired
    static WebClient webClient;

    @Autowired
    static AuthDao authDao;

    public static void sendKakaoTalk(String message, String telNo, String template) {
        KakaoDto kakaoTalk = KakaoDto.builder()
                .service("2310086314")
                .message(message)
                .callbackNo("025677705")
                .subject("P플")
                .mobile(telNo)
                .template(template)
                .memberId("toncompany")
                .backupMessage(message)
                .backupProcessCode("001")
                .build();

        // Send Kakao Talk
        String response = webClient.post()
                .uri("https://talkapi.lgcns.com/request/kakaoBudal.json")
                .headers(header -> {
                    header.set("Accept", "application/json");
                    header.set("Content-Type", "application/json");
                    header.set("authToken", "yc7Zr24P2S2RcdDcI4D1dg==");
                    header.set("serverName", "ton");
                })
                .bodyValue(kakaoTalk)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(error -> {
                    throw new ClientException(ErrorCode.SERVER_ERROR);
                })
                .block();

        log.info("Kakao Talk Response : {}", response);

        // Kakao Talk Log INSERT
        int result = authDao.insertKakaoTalkLog(kakaoTalk, response);

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }
    }
}