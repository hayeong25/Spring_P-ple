package com.api.pple.controller;

import com.api.pple.dto.AdDto;
import com.api.pple.service.AdService;
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
@RequestMapping("/advertise")
public class AdController {
    @Autowired
    AdService adService;

    // 광고 - 광고 리스트
    @PostMapping("/list")
    public ResponseEntity<List<AdDto>> getAdList(HttpServletRequest servletRequest) {
        log.info("AdController getAdList Header : {}", servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(adService.getAdList(accessToken));
    }

    // 광고 - 광고 상세
    @GetMapping("/detail/{adCode}")
    public ResponseEntity<AdDto> getAdDetail(@PathVariable("adCode")String adCode, HttpServletRequest servletRequest) {
        log.info("AdController getAdDetail AdCode : {}, Header : {}", adCode, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(adService.getAdDetail(adCode, accessToken));
    }

    // 광고 - 광고 등록
    @PostMapping("/register")
    public ResponseEntity<String> registerAd(@RequestBody @Valid AdDto request, HttpServletRequest servletRequest) {
        log.info("AdController registerAd request: {}, Header : {}", request, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(adService.registerAd(request, accessToken));
    }

    // 광고 - 광고 삭제
    @PutMapping("/delete")
    public ResponseEntity<String> deleteAd(String adCode, HttpServletRequest servletRequest) {
        log.info("AdController deleteAd request: {}, Header : {}", adCode, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(adService.deleteAd(adCode, accessToken));
    }
}