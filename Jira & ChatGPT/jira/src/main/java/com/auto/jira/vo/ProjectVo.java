package com.auto.jira.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectVo {

    private Long id;
    private String key;
    private String name;

    @Builder
    private ProjectVo(Long id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public static ProjectVo of(Long id, String key, String name) {
        return ProjectVo.builder()
                .id(id)
                .key(key)
                .name(name)
                .build();
    }
}
