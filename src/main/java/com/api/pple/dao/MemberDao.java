package com.api.pple.dao;

import com.api.pple.entity.Member;
import com.api.pple.entity.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDao {
    Member getMemberById(String id);

    int insertMember(Member request);

    Member getMemberByToken(String accessToken);

    String getMemberIdByNameAndTelNo(String name, String telNo);

    int certifyBusiness(Member member);

    int updateMemberPoint(Point point);

    int getMemberPoint(String id);

    List<Point> getMemberPointHistory(String id);

    int getMonthlySales(String id);

    int getTotalSales(String id);

    int updatePassword(String id, String password);

    int updateTelNo(String id, String telNo);

    int withdraw(String id);
}