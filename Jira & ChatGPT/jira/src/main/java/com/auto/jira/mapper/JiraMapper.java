package com.auto.jira.mapper;

import com.auto.jira.dto.response.ListResponse;
import com.auto.jira.dto.request.SummaryDTO;
import com.auto.jira.vo.MemberVo;
import com.auto.jira.vo.ProjectVo;
import com.auto.jira.vo.summary.IssueSummaryVo;
import com.auto.jira.vo.summary.SummaryVo;
import kong.unirest.core.json.JSONException;
import kong.unirest.core.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JiraMapper {

    public ProjectVo toProjectVo(JSONObject object) {
        if(object == null) return null;
        return ProjectVo.of(
                object.getLong("id"),
                object.getString("key"),
                object.getString("name")
        );
    }

    public MemberVo toMemberVo(JSONObject object) {
        if(object == null) return null;

        try {
            return MemberVo.of(
                    object.getString("displayName"),
                    object.getString("emailAddress")
            );
        } catch (JSONException e) {
            return MemberVo.of(
                    object.getString("displayName"),
                    null
            );
        }
    }

    public IssueSummaryVo toIssueSummaryVo(JSONObject object) {
        if(object == null) return null;
        JSONObject fieldsObject = object.getJSONObject("fields");

        try {
            return IssueSummaryVo.of(
                    object.getString("key"),
                    fieldsObject.getJSONObject("assignee").getString("displayName"),
                    fieldsObject.getString(("summary")),
                    fieldsObject.getString("description"),
                    fieldsObject.getJSONObject("status").getString("name"),
                    fieldsObject.getString("created")
            );
        } catch (JSONException e) {
            return IssueSummaryVo.of(
                    object.getString("key"),
                    null,
                    fieldsObject.getString(("summary")),
                    fieldsObject.getString("description"),
                    fieldsObject.getJSONObject("status").getString("name"),
                    fieldsObject.getString("created")
            );
        }
    }

    public SummaryDTO toSummaryDTO(List<SummaryVo> summaryVoList) {
        return SummaryDTO.from(summaryVoList);
    }

    public ListResponse toListResponse(List<?> list) {
        return ListResponse.from(list);
    }
}
