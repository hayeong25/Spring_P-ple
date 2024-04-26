package com.api.pple.service;

import com.api.pple.dao.AdDao;
import com.api.pple.dto.request.AdRequest;
import com.api.pple.dto.response.AdResponse;
import com.api.pple.entity.Advertise;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AdService {
    @Autowired
    AdDao adDao;

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public List<AdResponse> getAdList() {
        List<Advertise> advertiseList = Optional.of(adDao.getAdList())
                                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        return advertiseList.stream()
                .map(m -> new AdResponse(m.getAdvertiseCode(), m.getAdvertiseManufacturerCompanyName(), m.getAdvertiseImage(),
                        m.getAdvertiseUrl(), m.getAdvertiseDivision(), m.getAdvertiseState(), m.getAdvertiseEnrollmentDate()))
                .toList();
    }

    public AdResponse getAdDetail(String advertiseCode) {
        Advertise adDetail = Optional.of(adDao.getAdDetail(advertiseCode))
                                     .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        return modelMapper.map(adDetail, AdResponse.class);
    }

    public String registerAd(AdRequest.Register request) {
        Advertise advertise = Advertise.builder()
                .advertiseManufacturerCompanyName(request.getCompanyName())
                .advertiseImage(request.getImage())
                .advertiseUrl(request.getUrl())
                .advertiseDivision("신청")
                .advertiseState("신청")
                .advertiseEnrollmentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .build();

        int result = Optional.of(adDao.registerAd(advertise))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        return advertise.getAdvertiseCode();
    }

    public String deleteAd(String advertiseCode) {
        int result = Optional.of(adDao.deleteAd(advertiseCode))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }

        return advertiseCode;
    }
}