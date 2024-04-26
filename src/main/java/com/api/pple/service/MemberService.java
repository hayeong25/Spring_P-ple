package com.api.pple.service;

import com.api.pple.dao.AuthDao;
import com.api.pple.dao.MemberDao;
import com.api.pple.dto.request.MemberRequest;
import com.api.pple.dto.response.MemberResponse;
import com.api.pple.entity.Member;
import com.api.pple.entity.Point;
import com.api.pple.exception.ClientException;
import com.api.pple.utils.ErrorCode;
import com.api.pple.utils.KakaoTalk;
import com.api.pple.utils.Token;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    AuthDao authDao;

    @Autowired
    MemberDao memberDao;

    private final static int USE_N = 0;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public String join(MemberRequest.Join request) {
        Member member = Optional.of(memberDao.getMemberById(request.getId()))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (!ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.DUPLICATE_ID);
        }

        member = Member.builder()
                       .memberId(request.getId())
                       .memberPassword(DigestUtils.sha256Hex(request.getPassword()))
                       .memberName(request.getName())
                       .memberType("일반회원")
                       .memberContact(request.getTelNo())
                       .memberEmail(request.getEmail())
                       .memberLevel(1)
                       .memberZipcode(request.getZipcode())
                       .memberAddress(request.getAddress())
                       .memberUseYn(1)
                       .memberEnrollmentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                       .build();

        // INSERT MEMBER
        int result = Optional.of(memberDao.insertMember(member))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

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

    public MemberResponse.Login login(MemberRequest.Login request) {
        Member member = Optional.of(memberDao.getMemberById(request.getId()))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        // useYn validation
        if (member.getMemberUseYn() == USE_N) {
            throw new ClientException(ErrorCode.INVALID_ACCOUNT);
        }

        // password validation
        String password = DigestUtils.sha256Hex(request.getPassword());

        if (!request.getPassword().equals(password)) {
            throw new ClientException(ErrorCode.INVALID_PASSWORD);
        }

        // accessToken 생성
        String accessToken = RandomStringUtils.randomAlphanumeric(20);

        // INSERT accessToken
        int result = Optional.of(authDao.insertToken(request.getId(), accessToken))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }

        return new MemberResponse.Login(member.getMemberId(), member.getMemberName(), accessToken);
    }

    public String findId(MemberRequest.FindId request) {
        String id = Optional.of(memberDao.getMemberIdByNameAndTelNo(request.getName(), request.getTelNo()))
                            .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(id)) {
            throw new ClientException(ErrorCode.INVALID_ACCOUNT);
        }

        return id;
    }

    public String findPw(MemberRequest.FindPw request) {
        Member member = Optional.of(memberDao.getMemberById(request.getId()))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        if (!member.getMemberEmail().equals(request.getEmail()) || !member.getMemberContact().equals(request.getTelNo())) {
            throw new ClientException(ErrorCode.INVALID_ACCOUNT);
        }

        return member.getMemberId();
    }

    public String certifyBusiness(MemberRequest.Business request, String accessToken) {
        Member member = memberDao.getMemberByToken(accessToken);

        member.setMemberType("정회원");
        member.setMemberBusinessNo(request.getBusinessNo());
        member.setMemberCompanyName(request.getCompanyName());

        int result = Optional.of(memberDao.certifyBusiness(member))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        String message = "[P플] 사업자 인증 완료 안내" +
                "\n" + member.getMemberName() + "님의 사업자 인증이 완료되었습니다." +
                "\n지금 바로 사업자 회원에게만 제공되는 다양한 서비스를 이용하실 수 있습니다." +
                "\n항상 노력하는 P플 서비스가 될 수 있도록 최선을 다하겠습니다.";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, member.getMemberContact(), "10052");

        return member.getMemberId();
    }

    public String updateMemberPoint(MemberRequest.Point request) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        Point point = Point.builder()
                           .memberId(request.getId())
                           .memberPointType(request.getType())
                           .memberPointDivision(request.getStatus())
                           .memberPoint(request.getPoint())
                           .memberPointGetContent(request.getContent())
                           .memberPointEnrollmentDate(request.getStatus().equals("적립") ? dateTime : null)
                           .memberPointUseDate(request.getStatus().equals("사용") ? dateTime : null)
                           .build();

        int result = Optional.of(memberDao.updateMemberPoint(point))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        return point.getMemberPointDivision();
    }

    public int getMemberPoint(String accessToken) {
        Member member = Optional.of(memberDao.getMemberByToken(accessToken))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        // 총 point 계산
        return Optional.of(memberDao.getMemberPoint(member.getMemberId()))
                       .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));
    }

    public List<MemberResponse.Point> getMemberPointHistory(String accessToken) {
        Token.checkToken(accessToken);

        Member member = Optional.of(memberDao.getMemberByToken(accessToken))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        List<Point> pointHistoryList = memberDao.getMemberPointHistory(member.getMemberId());

        return pointHistoryList.stream()
                               .map(m -> new MemberResponse.Point(m.getMemberId(), m.getMemberPointType(),
                                       m.getMemberPointDivision(), m.getMemberPoint(), m.getMemberPointGetContent(),
                                       m.getMemberPointEnrollmentDate(), m.getMemberPointUseDate()))
                               .toList();
    }

    public MemberResponse.Sales getMemberMonthlySales(String accessToken) {
        Token.checkToken(accessToken);

        Member member = Optional.of(memberDao.getMemberByToken(accessToken))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        // 월 매출
        int sales = Optional.of(memberDao.getMonthlySales(member.getMemberId()))
                            .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        // 누적 총 매출
        int totalSales = Optional.of(memberDao.getTotalSales(member.getMemberId()))
                                 .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        return new MemberResponse.Sales(sales, totalSales);
    }

    public void modifyPassword(MemberRequest.Modify request) {
        Member member = Optional.of(memberDao.getMemberById(request.getId()))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        // compare password
        String password = DigestUtils.sha256Hex(request.getPrePassword());

        if (!member.getMemberPassword().equals(password)) {
            throw new ClientException(ErrorCode.INVALID_PASSWORD);
        }

        // UPDATE MEMBER
        int result = Optional.of(memberDao.updatePassword(request.getId(), DigestUtils.sha256Hex(request.getNewPassword())))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        String message = "[P플] 비밀번호 변경 안내" +
                "\n" + member.getMemberCompanyName() + "님의 비밀번호가 변경되었습니다." +
                "\n변경한 비밀번호로 다시 로그인 하시기 바랍니다." +
                "\n본인이 요청한 사항이 아닐 경우 즉시 비밀번호를 재설정해 주시길 바랍니다.";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, member.getMemberContact(), "10051");
    }

    public void modifyTelNo(MemberRequest.Modify request, String accessToken) {
        Token.checkToken(accessToken);

        Member member = Optional.of(memberDao.getMemberById(request.getId()))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        // UPDATE MEMBER
        int result = Optional.of(memberDao.updateTelNo(request.getId(), request.getTelNo()))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        String message = "[P플] 휴대폰 번호 변경 안내" +
                "\n" + member.getMemberName() + "님의 회원 정보가 정상적으로 변경되었습니다." +
                "\n변경된 내용은 마이페이지에서 확인 가능합니다.";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, member.getMemberContact(), "10050");
    }

    public void logout(String accessToken) {
        // DELETE accessToken
        int result = Optional.of(authDao.deleteToken(accessToken))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.DELETE_FAIL);
        }
    }

    public void withdraw(MemberRequest.Login request, String accessToken) {
        Token.checkToken(accessToken);

        Member member = Optional.of(memberDao.getMemberById(request.getId()))
                                .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (ObjectUtils.isEmpty(member)) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        // UPDATE MEMBER
        int result = Optional.of(memberDao.withdraw(request.getId()))
                             .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }

        String message = """
                [P플] 회원 탈퇴 안내
                P플 회원 탈퇴 처리가 완료되었습니다.
                그동안 P플을 이용해 주셔서 감사합니다.
                보다 나은 서비스와 혜택으로 다시 찾아뵙겠습니다.""";

        // 카카오톡 알림톡
        KakaoTalk.sendKakaoTalk(message, member.getMemberContact(), "10013");
    }
}