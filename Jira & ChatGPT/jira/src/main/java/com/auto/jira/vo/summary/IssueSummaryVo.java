package com.auto.jira.vo.summary;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueSummaryVo {

    private String key;
    private String assigneeName;
    private String issueSummary;
    private String issueDescription;
    private String status;
    private String created;

    @Builder
    private IssueSummaryVo(String key, String assigneeName, String issueSummary, String issueDescription, String status, String created) {
        this.key = key;
        this.assigneeName = assigneeName;
        this.issueSummary = issueSummary;
        this.issueDescription = issueDescription;
        this.status = status;
        this.created = created;
    }

    public static IssueSummaryVo of(String key, String assigneeName, String issueSummary, String issueDescription, String status, String created) {
        return IssueSummaryVo.builder()
                .key(key)
                .assigneeName(assigneeName)
                .issueSummary(issueSummary)
                .issueDescription(issueDescription)
                .status(status)
                .created(created)
                .build();
    }
}
