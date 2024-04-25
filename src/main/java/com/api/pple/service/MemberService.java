package com.api.pple.service;

import com.api.pple.dao.AuthDao;
import com.api.pple.dao.MemberDao;
import com.api.pple.dto.MemberDto;
import com.api.pple.dto.PointDto;
import com.api.pple.dto.SalesDto;
import com.api.pple.entity.Member;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import com.api.pple.utils.KakaoTalk;
import com.api.pple.utils.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class MemberService {
    @Autowired
    AuthDao authDao;

    @Autowired
    MemberDao memberDao;

    private final static int USE_N = 0;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public String join(MemberDto.Join request) {
        Member member = memberDao.getMemberById(request.getId());

        if (!ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.DUPLICATE_ID);
        }

        // password -> SHA256 Hash
        request.setPassword(DigestUtils.sha256Hex(request.getPassword()));

        // INSERT MEMBER
        int result = memberDao.insertMember(request);

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        String message = "[P플] 회원가입 완료 안내\n안녕하세요 " + request.getName() + "님!" +
                "\nP플 회원가입을 축하드립니다." +
                "\nP플은 소상공인의 비즈니스 성장을 위해 다양한 혜택을 제공하고 있습니다." +
                "\n사업자 인증을 하시면 정회원에게만 제공되는 매출 리워드 서비스와 매출 관리 서비스를 이용하실 수 있습니다.";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, request.getTelNo(), "10053");

        return request.getId();
    }

    public MemberDto login(MemberDto.Login request) {
        Member member = memberDao.getMemberById(request.getId());

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        // useYn validation
        if (member.getUseYn() == USE_N) {
            throw new ClientException(ErrorCode.INVALID_ACCOUNT);
        }

        // password validation
        String password = DigestUtils.sha256Hex(request.getPassword());

        if (!request.getPassword().equals(password)) {
            throw new ClientException(ErrorCode.INVALID_PASSWORD);
        }

        // accessToken 생성
        String accessToken = RandomStringUtils.randomAlphanumeric(20);

        log.info("accessToken : {}", accessToken);

        // INSERT accessToken
        int result = authDao.insertToken(request.getId(), accessToken);

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        return MemberDto.builder()
                .id(member.getId())
                .accessToken(accessToken)
                .name(member.getName())
                .build();
    }

    public String findId(MemberDto.FindId request) {
        String id = memberDao.getMemberIdByNameAndTelNo(request.getName(), request.getTelNo());

        if (ObjectUtils.isEmpty(id)) {
            throw new ClientException(ErrorCode.INVALID_ACCOUNT);
        }

        return id;
    }

    public String findPw(MemberDto.FindPw request) {
        Member member = memberDao.getMemberById(request.getId());

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        if (!member.getEmail().equals(request.getEmail()) || !member.getTelNo().equals(request.getTelNo())) {
            throw new ClientException(ErrorCode.INVALID_ACCOUNT);
        }

        return member.getId();
    }

    public String updateMemberPoint(PointDto request, String accessToken) {
        Token.checkToken(accessToken);

        Member member = memberDao.getMemberByToken(accessToken);

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        PointDto point = PointDto.builder()
                .id(member.getId())
                .type(request.getType())
                .status(request.getStatus())
                .point(request.getPoint())
                .getDateTime(request.getStatus().equals("적립") ? dateTime : null)
                .useDateTime(request.getStatus().equals("사용") ? dateTime : null)
                .build();

        int result = memberDao.updateMemberPoint(point);

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        return point.getStatus();
    }

    public int getMemberPoint(String accessToken) {
        Token.checkToken(accessToken);

        Member member = memberDao.getMemberByToken(accessToken);

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        // 총 point 계산
        return memberDao.getMemberPoint(member.getId());
    }

    public List<PointDto> getMemberPointHistory(String accessToken) {
        Token.checkToken(accessToken);

        Member member = memberDao.getMemberByToken(accessToken);

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        return memberDao.getMemberPointHistory(member.getId());
    }

    public SalesDto getMemberMonthlySales(String accessToken) {
        Token.checkToken(accessToken);

        Member member = memberDao.getMemberByToken(accessToken);

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        // 월 매출
        int sales = memberDao.getMonthlySales(member.getId());

        // 누적 총 매출
        int totalSales = memberDao.getTotalSales(member.getId());

        return new SalesDto(sales, totalSales);
    }

    public void modifyPassword(MemberDto.Modify request) {
        Member member = memberDao.getMemberById(request.getId());

        if (ObjectUtils.isEmpty(member.getPassword())) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        // compare password
        String password = DigestUtils.sha256Hex(request.getPrePassword());

        if (!member.getPassword().equals(password)) {
            throw new ClientException(ErrorCode.INVALID_PASSWORD);
        }

        // UPDATE MEMBER
        int result = memberDao.updatePassword(request.getId(), DigestUtils.sha256Hex(request.getNewPassword()));

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        String message = "[P플] 비밀번호 변경 안내" +
                "\n" + member.getName() + "님의 비밀번호가 변경되었습니다." +
                "\n변경한 비밀번호로 다시 로그인 하시기 바랍니다." +
                "\n본인이 요청한 사항이 아닐 경우 즉시 비밀번호를 재설정해 주시길 바랍니다.";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, member.getTelNo(), "10051");
    }

    public void modifyTelNo(MemberDto.Modify request, String accessToken) {
        Token.checkToken(accessToken);

        Member member = memberDao.getMemberById(request.getId());

        if (ObjectUtils.isEmpty(member.getPassword())) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        // UPDATE MEMBER
        int result = memberDao.updateTelNo(request.getId(), request.getTelNo());

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        String message = "[P플] 휴대폰 번호 변경 안내" +
                "\n" + member.getName() + "님의 회원 정보가 정상적으로 변경되었습니다." +
                "\n변경된 내용은 마이페이지에서 확인 가능합니다.";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, member.getTelNo(), "10050");
    }

    public void logout(String accessToken) {
        // DELETE accessToken
        int result = authDao.deleteToken(accessToken);

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }
    }

    public void withdraw(MemberDto.Login request, String accessToken) {
        Token.checkToken(accessToken);

        Member member = memberDao.getMemberById(request.getId());

        if (ObjectUtils.isEmpty(member.getPassword())) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        // UPDATE MEMBER
        int result = memberDao.withdraw(request.getId());

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        String message = """
                [P플] 회원 탈퇴 안내
                P플 회원 탈퇴 처리가 완료되었습니다.
                그동안 P플을 이용해 주셔서 감사합니다.
                보다 나은 서비스와 혜택으로 다시 찾아뵙겠습니다.""";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, member.getTelNo(), "10013");
    }
}