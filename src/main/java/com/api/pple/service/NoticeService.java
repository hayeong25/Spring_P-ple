package com.api.pple.service;

import com.api.pple.dao.NoticeDao;
import com.api.pple.dto.request.NoticeRequest;
import com.api.pple.dto.response.NoticeResponse;
import com.api.pple.entity.Notice;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    @Autowired
    NoticeDao noticeDao;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ModelMapper modelMapper = new ModelMapper();

    public List<NoticeResponse> getNoticeList() {
        List<Notice> noticeList = Optional.of(noticeDao.getNoticeList())
                                          .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        return noticeList.stream()
                         .map(m -> new NoticeResponse(m.getNoticeId(), m.getNoticeTitle(), m.getNoticeContent(),
                                 m.getNoticeUseYn(), m.getNoticeFixedYn(), m.getNoticeEnrollmentDate()))
                         .toList();
    }

    public NoticeResponse getNoticeDetail(String noticeId) {
        Notice noticeDetail = Optional.of(noticeDao.getNoticeDetail(noticeId))
                                      .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(noticeDetail)) {
            throw new ClientException(ErrorCode.SELECT_FAIL);
        }

        return modelMapper.map(noticeDetail, NoticeResponse.class);
    }

    public String registerNotice(NoticeRequest request) {
        Notice notice = Notice.builder()
                              .noticeTitle(request.getTitle())
                              .noticeContent(request.getContent())
                              .noticeUseYn(1)
                              .noticeFixedYn(request.getFixedYn())
                              .noticeEnrollmentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                              .build();

        int result = Optional.of(noticeDao.registerNotice(notice))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        return notice.getNoticeId();
    }

    public String deleteNotice(String noticeId) {
        int result = Optional.of(noticeDao.deleteNotice(noticeId))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }

        return noticeId;
    }
}