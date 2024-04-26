package com.api.pple.dao;

import com.api.pple.entity.Advertise;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdDao {
    List<Advertise> getAdList();

    Advertise getAdDetail(String advertiseCode);

    int registerAd(Advertise request);

    int deleteAd(String advertiseCode);
}