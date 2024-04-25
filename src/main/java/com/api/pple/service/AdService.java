package com.api.pple.service;

import com.api.pple.dao.AdDao;
import com.api.pple.dto.AdDto;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import com.api.pple.utils.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class AdService {
    @Autowired
    AdDao adDao;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public List<AdDto> getAdList(String accessToken) {
        Token.checkToken(accessToken);

        return adDao.getAdList();
    }


    public AdDto getAdDetail(String adCode, String accessToken) {
        Token.checkToken(accessToken);

        AdDto adDetail = adDao.getAdDetail(adCode);

        if (ObjectUtils.isEmpty(adDetail)) {
            throw new ClientException(ErrorCode.SELECT_FAIL);
        }

        return adDetail;
    }

    public String registerAd(AdDto request, String accessToken) {
        Token.checkToken(accessToken);

        request.setDivision("신청");
        request.setStatus("신청");
        request.setEnrollmentDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));

        int result = adDao.registerAd(request);

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        return request.getCode();
    }

    public String deleteAd(String adCode, String accessToken) {
        Token.checkToken(accessToken);

        int result = adDao.deleteAd(adCode);

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }

        return adCode;
    }
}