package com.auto.jira.service;

import com.auto.jira.dto.request.SummaryDTO;
import com.auto.jira.helper.JiraHelper;
import com.auto.jira.vo.IssueVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JiraService {

    private final JiraHelper jiraHelper;

    public IssueVo getIssueVo(String issueKey) {
        return jiraHelper.getIssue(issueKey);
    }

    public SummaryDTO searchByCreated(String projectKey, Date fromDate, Date toDate) {
        return jiraHelper.searchIssueByCreated(projectKey, fromDate, toDate);
    }

    public SummaryDTO searchByStatus(String projectKey, String status) {
        return jiraHelper.searchIssueByStatus(projectKey, status);
    }
}
