package com.auto.jira.service;

import com.auto.jira.dto.request.SummaryDTO;
import com.auto.jira.helper.OpenAIHepler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final OpenAIHepler openAIHepler;

    public String summaryWithCreated(Locale locale, SummaryDTO summaryDTO, Date fromDate, Date toDate) throws IOException {
        return openAIHepler.summaryWithCreated(locale, summaryDTO, fromDate, toDate);
    }

    public String summaryWithStatus(Locale locale, SummaryDTO summaryDTO, String status) throws IOException {
        return openAIHepler.summaryWithStatus(locale, summaryDTO, status);
    }
}
