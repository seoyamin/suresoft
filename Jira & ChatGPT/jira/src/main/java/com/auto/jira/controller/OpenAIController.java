package com.auto.jira.controller;

import com.auto.jira.SearchFilter;
import com.auto.jira.dto.request.SummaryDTO;
import com.auto.jira.service.JiraService;
import com.auto.jira.service.OpenAIService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class OpenAIController {

    private final JiraService jiraService;

    private final OpenAIService openAIService;


    @GetMapping("/{filter}/{projectKey}")
    public ResponseEntity<String> summaryIssuesByCreated(
            HttpServletRequest httpServletRequest,
            @PathVariable SearchFilter filter,
            @PathVariable String projectKey,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(required = false) String status) throws IOException {

        Locale currentLocale = httpServletRequest.getLocale();

        SummaryDTO summaryDTO;
        String answer = null;

        switch (filter) {
            case CREATED -> {
                summaryDTO = jiraService.searchByCreated(projectKey, fromDate, toDate);
                answer = openAIService.summaryWithCreated(currentLocale, summaryDTO, fromDate, toDate);
            }
            case STATUS -> {
                summaryDTO = jiraService.searchByStatus(projectKey, status);
                answer = openAIService.summaryWithStatus(currentLocale, summaryDTO, status);
            }
        }
        return ResponseEntity.ok(answer);
    }

}
