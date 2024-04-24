package com.api.pple.dao;

import com.api.pple.dto.MemberDto;
import com.api.pple.dto.PointDto;
import com.api.pple.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDao {
    Member getMemberById(String id);

    int insertMember(MemberDto.Join request);

    Member getMemberByToken(String accessToken);

    String getMemberIdByNameAndTelNo(String name, String telNo);

    int getMemberPoint(String id);

    List<PointDto> getMemberPointHistory(String id);

    int getMonthlySales(String id);

    int getTotalSales(String id);

    int updatePassword(String id, String password);

    int updateTelNo(String id, String telNo);

    int withdraw(String id);
}