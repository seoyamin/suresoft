package com.auto.jira.controller;

import com.auto.jira.SearchFilter;
import com.auto.jira.dto.request.SummaryDTO;
import com.auto.jira.service.JiraService;
import com.auto.jira.vo.IssueVo;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class JiraController {

    private final JiraService jiraService;

    @GetMapping("/issue/{issueKey}")
    public ResponseEntity<IssueVo> getIssueVo(@PathVariable String issueKey) {
        return ResponseEntity.ok(jiraService.getIssueVo(issueKey));
    }

    @GetMapping("/{filter}/{projectKey}")
    public SummaryDTO getSummaryDTO(
            @PathVariable SearchFilter filter,
            @PathVariable String projectKey,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(required = false) String status) {
        
        SummaryDTO summaryDTO = null;

        switch (filter) {
            case CREATED -> {
                summaryDTO =  jiraService.searchByCreated(projectKey, fromDate, toDate);
            }
            case STATUS -> {
                summaryDTO = jiraService.searchByStatus(projectKey, status);
            }
        }
        
        return summaryDTO;
    }

}
