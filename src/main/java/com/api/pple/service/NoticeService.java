package com.api.pple.service;

import com.api.pple.dao.MemberDao;
import com.api.pple.dao.NoticeDao;
import com.api.pple.dto.NoticeDto;
import com.api.pple.entity.Member;
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
public class NoticeService {
    @Autowired
    NoticeDao noticeDao;

    @Autowired
    MemberDao memberDao;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public List<NoticeDto> getNoticeList(String accessToken) {
        Token.checkToken(accessToken);

        return noticeDao.getNoticeList();
    }


    public NoticeDto getNoticeDetail(String noticeId, String accessToken) {
        Token.checkToken(accessToken);

        NoticeDto noticeDetail = noticeDao.getNoticeDetail(noticeId);

        if (ObjectUtils.isEmpty(noticeDetail)) {
            throw new ClientException(ErrorCode.SELECT_FAIL);
        }

        return noticeDetail;
    }

    public NoticeDto registerNotice(NoticeDto request, String accessToken) {
        Token.checkToken(accessToken);

        Member member = memberDao.getMemberByToken(accessToken);

        // set writer(memberId), createDateTime
        request.setWriter(member.getId());
        request.setEnrollmentDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));

        int result = noticeDao.registerNotice(request);

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        return new NoticeDto(request.getId(), member.getId(), request.getTitle(), request.getContent(), 1, request.getEnrollmentDateTime());
    }

    public String deleteNotice(String noticeId, String accessToken) {
        Token.checkToken(accessToken);

        Member member = memberDao.getMemberByToken(accessToken);

        NoticeDto notice = noticeDao.getNoticeDetail(noticeId);

        if (!notice.getWriter().equals(member.getId())) {
            throw new ClientException(ErrorCode.INVALID_ACCOUNT);
        }

        int result = noticeDao.deleteNotice(noticeId);

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }

        return noticeId;
    }
}