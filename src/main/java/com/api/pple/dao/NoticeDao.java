package com.api.pple.dao;

import com.api.pple.dto.NoticeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeDao {
    List<NoticeDto> getNoticeList();

    NoticeDto getNoticeDetail(String noticeId);

    int registerNotice(NoticeDto request);

    int deleteNotice(String noticeId);
}