package com.auto.jira.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberVo {

    private final String name;
    private final String email;


    @Builder
    private MemberVo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static MemberVo of(String name, String email) {
        return MemberVo.builder()
                .name(name)
                .email(email)
                .build();
    }
}
