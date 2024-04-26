package com.api.pple.controller;

import com.api.pple.dto.request.NoticeRequest;
import com.api.pple.dto.response.NoticeResponse;
import com.api.pple.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    /*
    * 공지사항 - 공지 리스트
    */
    @PostMapping("/list")
    public ResponseEntity<List<NoticeResponse>> getNoticeList() {
        return ResponseEntity.ok(noticeService.getNoticeList());
    }

    /*
    * 공지사항 - 공지 상세
    */
    @GetMapping("/detail/{noticeId}")
    public ResponseEntity<NoticeResponse> getNoticeDetail(@PathVariable("noticeId")String noticeId) {
        return ResponseEntity.ok(noticeService.getNoticeDetail(noticeId));
    }

    /*
    * 공지사항 - 공지 등록
    */
    @PostMapping("/register")
    public ResponseEntity<String> registerNotice(@RequestBody @Valid NoticeRequest request) {
        return ResponseEntity.ok(noticeService.registerNotice(request));
    }

    /*
    * 공지사항 - 공지 삭제
    */
    @PutMapping("/delete")
    public ResponseEntity<String> deleteNotice(String noticeId) {
        return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
    }
}