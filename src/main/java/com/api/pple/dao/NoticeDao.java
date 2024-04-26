package com.api.pple.dao;

import com.api.pple.entity.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeDao {
    List<Notice> getNoticeList();

    Notice getNoticeDetail(String noticeId);

    int registerNotice(Notice request);

    int deleteNotice(String noticeId);
}