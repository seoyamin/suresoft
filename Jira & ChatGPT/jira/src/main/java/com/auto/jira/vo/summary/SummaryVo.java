package com.auto.jira.vo.summary;

import lombok.Builder;

import java.util.List;

public class SummaryVo {

    private IssueSummaryVo issueSummaryVo;

    private List<IssueSummaryVo> subTaskSummaryVos;

    @Builder
    private SummaryVo(IssueSummaryVo issueSummaryVo, List<IssueSummaryVo> subTaskSummaryVos) {
        this.issueSummaryVo = issueSummaryVo;
        this.subTaskSummaryVos = subTaskSummaryVos;
    }

    public static SummaryVo of(IssueSummaryVo issueSummaryVo, List<IssueSummaryVo> subTaskSummaryVos) {
        return SummaryVo.builder()
                .issueSummaryVo(issueSummaryVo)
                .subTaskSummaryVos(subTaskSummaryVos)
                .build();
    }
}
