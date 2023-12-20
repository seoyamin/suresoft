package com.auto.jira.helper;

import com.auto.jira.dto.request.SummaryDTO;
import com.auto.jira.mapper.JiraMapper;
import com.auto.jira.vo.IssueVo;
import com.auto.jira.vo.summary.IssueSummaryVo;
import com.auto.jira.vo.summary.SummaryVo;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JiraHelper {

    public static final String JIRA_DOMAIN = "https://suresofttech.atlassian.net";

    private final JiraMapper jiraMapper;

    @Value("${jira.api-token}")
    private String apiToken;

    public IssueVo getIssue(String issueKey) {
        String requestPath = JIRA_DOMAIN + "/rest/agile/1.0/issue/" + issueKey + "?fields=project,creator,assignee,created,summary";

        HttpResponse<JsonNode> response = Unirest.get(requestPath)
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + apiToken)
                .asJson();

        JSONObject jsonObj = response.getBody().getObject();
        JSONObject fieldsObj = jsonObj.getJSONObject("fields");

        return IssueVo.of(
                jsonObj.getLong("id"),
                jsonObj.getString("key"),
                fieldsObj.getString("created"),
                fieldsObj.getString("summary"),
                jiraMapper.toProjectVo((JSONObject) fieldsObj.get("project")),
                jiraMapper.toMemberVo((JSONObject) fieldsObj.get("creator")),
                jiraMapper.toMemberVo((JSONObject) fieldsObj.get("assignee"))
        );
    }

    public SummaryDTO searchIssueByCreated(String projectKey, Date fromDate, Date toDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromDateStr = simpleDateFormat.format(fromDate);
        String toDateStr = simpleDateFormat.format(toDate);

        String requestPath = JIRA_DOMAIN + "/rest/api/2/search";

        String jql = new StringBuilder()
                .append("project = ").append(projectKey)
                .append(" AND issuetype =").append("weekly")
                .append(" AND created >= ").append(fromDateStr)
                .append(" AND created <= ").append(toDateStr)
                .append(" order by created ASC")
                .toString();

        HttpResponse<JsonNode> response = Unirest.get(requestPath)
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + apiToken)
                .queryString("jql", jql)
                .queryString("fields", "description,created,summary,status")
                .asJson();

        List<JSONObject> responseList = response.getBody().getObject().getJSONArray("issues").toList();

        if(!responseList.isEmpty()) {
            List<SummaryVo> summaryVoList = new ArrayList<>();
            for(JSONObject object : responseList) {
                summaryVoList.add(createIssueSummaryVo(object));
            }

            return jiraMapper.toSummaryDTO(summaryVoList);
        }

        return null;
    }

    /* parentKey -> List<JSONObject> of subTask */
    private List<JSONObject> searchSubTaskByParent (String parentKey) {
        String requestPath = JIRA_DOMAIN + "/rest/api/2/search";

        HttpResponse<JsonNode> response = Unirest.get(requestPath)
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + apiToken)
                .queryString("jql", "parent = " + parentKey + " order by created ASC")
                .queryString("fields", "description,created,summary,status")
                .asJson();

        return response.getBody().getObject().getJSONArray("issues").toList();
    }


    /* JSONObject of issue -> SummaryVo */
    private SummaryVo createIssueSummaryVo(JSONObject weeklyIssueObj) {
        IssueSummaryVo issueSummaryVo = jiraMapper.toIssueSummaryVo(weeklyIssueObj);

        List<JSONObject> subTaskList = searchSubTaskByParent(issueSummaryVo.getKey());
        List<IssueSummaryVo> subTaskSummaryVoList = new ArrayList<>();
        for(JSONObject object : subTaskList) {
            subTaskSummaryVoList.add(jiraMapper.toIssueSummaryVo(object));
        }

        return SummaryVo.of(issueSummaryVo, subTaskSummaryVoList);
    }

    public SummaryDTO searchIssueByStatus(String projectKey, String status) {
        String requestPath = JIRA_DOMAIN + "/rest/api/2/search";

        String jql = new StringBuilder()
                .append("project = ").append(projectKey)
                .append(" AND status = \"").append(status).append("\"")
                .append(" order by created ASC")
                .toString();

        HttpResponse<JsonNode> response = Unirest.get(requestPath)
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + apiToken)
                .queryString("jql", jql)
                .queryString("fields", "description,created,summary,status")
                .asJson();

        List<JSONObject> responseList = response.getBody().getObject().getJSONArray("issues").toList();

        if(!responseList.isEmpty()) {
            List<SummaryVo> summaryVoList = new ArrayList<>();
            for(JSONObject object : responseList) {
                summaryVoList.add(createIssueSummaryVo(object));
            }

            return jiraMapper.toSummaryDTO(summaryVoList);
        }

        return null;
    }
}
