package com.api.pple.controller;

import com.api.pple.dto.NoticeDto;
import com.api.pple.service.NoticeService;
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
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    /*
    * 공지사항 - 공지 리스트
    */
    @PostMapping("/list")
    public ResponseEntity<List<NoticeDto>> getNoticeList(HttpServletRequest servletRequest) {
        log.info("NoticeController getNoticeList Header : {}", servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(noticeService.getNoticeList(accessToken));
    }

    /*
    * 공지사항 - 공지 상세
    */
    @GetMapping("/detail/{noticeId}")
    public ResponseEntity<NoticeDto> getNoticeDetail(@PathVariable("noticeId")String noticeId, HttpServletRequest servletRequest) {
        log.info("NoticeController getNoticeDetail requestBody : {}, Header : {}", noticeId, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(noticeService.getNoticeDetail(noticeId, accessToken));
    }

    /*
    * 공지사항 - 공지 등록
    */
    @PostMapping("/register")
    public ResponseEntity<NoticeDto> registerNotice(@RequestBody @Valid NoticeDto request, HttpServletRequest servletRequest) {
        log.info("NoticeController registerNotice requestBody : {}, Header : {}", request, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(noticeService.registerNotice(request, accessToken));
    }

    /*
    * 공지사항 - 공지 삭제
    */
    @PutMapping("/delete")
    public ResponseEntity<String> deleteNotice(String noticeId, HttpServletRequest servletRequest) {
        log.info("NoticeController registerNotice request : {}, Header : {}", noticeId, servletRequest);
        String accessToken = Token.getAccessTokenFromHeader(servletRequest);
        return ResponseEntity.ok(noticeService.deleteNotice(noticeId, accessToken));
    }
}