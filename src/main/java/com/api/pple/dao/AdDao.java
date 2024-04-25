package com.api.pple.dao;

import com.api.pple.dto.AdDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdDao {
    List<AdDto> getAdList();

    AdDto getAdDetail(String adCode);

    int registerAd(AdDto request);

    int deleteAd(String adCode);
}