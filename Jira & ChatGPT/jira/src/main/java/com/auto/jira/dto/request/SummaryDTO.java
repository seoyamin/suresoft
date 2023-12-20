package com.auto.jira.dto.request;

import com.auto.jira.vo.summary.SummaryVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SummaryDTO {

    List<SummaryVo> summaryVos;

    @Builder
    private SummaryDTO(List<SummaryVo> summaryVos) {
        this.summaryVos = summaryVos;
    }

    public static SummaryDTO from(List<SummaryVo> summaryVos) {
        return SummaryDTO.builder()
                .summaryVos(summaryVos)
                .build();
    }

}
