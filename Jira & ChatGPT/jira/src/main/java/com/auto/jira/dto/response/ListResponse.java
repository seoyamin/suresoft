package com.auto.jira.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse {

    private List<?> responseList;

    @Builder
    private ListResponse(List<?> responseList) {
        this.responseList = responseList;
    }

    public static ListResponse from (List<?> list) {
        return ListResponse.builder()
                .responseList(list)
                .build();
    }
}
