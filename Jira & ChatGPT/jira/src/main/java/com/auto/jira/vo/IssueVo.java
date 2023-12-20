package com.auto.jira.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueVo {

    private final Long id;
    private final String key;
    private final String createdAt;
    private final String summary;

    private final ProjectVo project;

    private final MemberVo creator;
    private final MemberVo assignee;


    @Builder
    private IssueVo(Long id, String key, String createdAt, String summary,
                    ProjectVo project, MemberVo creator, MemberVo assignee) {
        this.id = id;
        this.key = key;
        this.createdAt = createdAt;
        this.summary = summary;
        this.project = project;
        this.creator = creator;
        this.assignee = assignee;
    }

    public static IssueVo of(Long id, String key, String createdAt, String summary,
                             ProjectVo project, MemberVo creator, MemberVo assignee) {
        return IssueVo.builder()
                .id(id)
                .key(key)
                .createdAt(createdAt)
                .summary(summary)
                .project(project)
                .creator(creator)
                .assignee(assignee)
                .build();
    }
}
