package com.api.pple.controller;

import com.api.pple.dto.request.AdRequest;
import com.api.pple.dto.response.AdResponse;
import com.api.pple.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/advertise")
public class AdController {
    @Autowired
    AdService adService;

    /*
    * 광고 - 광고 리스트
    */
    @PostMapping("/list")
    public ResponseEntity<List<AdResponse>> getAdList() {
        return ResponseEntity.ok(adService.getAdList());
    }

    /*
    * 광고 - 광고 상세
    */
    @GetMapping("/detail/{advertiseCode}")
    public ResponseEntity<AdResponse> getAdDetail(@PathVariable("advertiseCode") String advertiseCode) {
        return ResponseEntity.ok(adService.getAdDetail(advertiseCode));
    }

    /*
    * 광고 - 광고 등록
    */
    @PostMapping("/register")
    public ResponseEntity<String> registerAd(@RequestBody @Valid AdRequest.Register request) {
        return ResponseEntity.ok(adService.registerAd(request));
    }

    /*
    * 광고 - 광고 삭제
    */
    @PutMapping("/delete")
    public ResponseEntity<String> deleteAd(String advertiseCode) {
        return ResponseEntity.ok(adService.deleteAd(advertiseCode));
    }
}