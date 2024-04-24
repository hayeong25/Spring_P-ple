package com.api.pple.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String id;
    private String password;
    private String name;
    private String memberType;
    private String telNo;
    private String email;
    private String memberLevel;
    private String businessNo;
    private String companyName;
    private String zipcode;
    private String address;
    private int useYn;
}