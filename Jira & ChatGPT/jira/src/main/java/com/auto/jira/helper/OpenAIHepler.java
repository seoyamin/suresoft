package com.auto.jira.helper;

import com.auto.jira.dto.request.SummaryDTO;
import com.google.gson.Gson;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
public class OpenAIHepler {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.request.path}")
    private String reqPath;

    private final MessageSource messageSource;

    private static final String KOR_MSG_PROPS_PATH = "src\\main\\resources\\messages_ko.properties";
    private static final String ENG_MSG_PROPS_PATH = "src\\main\\resources\\messages_en.properties";

    private GptPromptBody getGptPromptBody(Locale locale) {
        String instruction = messageSource.getMessage("instruction", null, locale);
        String template = messageSource.getMessage("template", null, locale);

        return GptPromptBody.builder()
                .instruction(instruction)
                .template(template)
                .build();


        /* Properties 파일에 직접 접근하여 읽어오는 방식
        Properties properties = new Properties();
        if(locale.getCountry().equals("KR")) properties.load(new FileInputStream(KOR_MSG_PROPS_PATH));
        else properties.load(new FileInputStream(ENG_MSG_PROPS_PATH));

        return GPTRequestBody.builder()
                .instruction(properties.getProperty("instruction"))
                .template(properties.getProperty("template"))
                .build();
         */
    }


    /* OpenAI 서버로 요청 */
    private String requestGPT(Locale locale, SummaryDTO summaryDTO) {
        GptPromptBody gptPromptBody = getGptPromptBody(locale);

        GptRequestMessage gptRequestMessage = GptRequestMessage.builder()
                .role("user")
                .content(gptPromptBody.instruction + "\n\n" + new Gson().toJson(summaryDTO) + "\n\n" + gptPromptBody.template)
                .build();

        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(gptRequestMessage));

        Unirest.config().reset();
        Unirest.config().connectTimeout(100000);

        HttpResponse<JsonNode> response = Unirest.post(reqPath)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .body(requestBody)
                .asJson();

        return  ((JSONObject) response.getBody().getObject().getJSONArray("choices").get(0))
                .getJSONObject("message").getString("content");
    }


    /* 동진님 서버로 요청 */
    private String requestDJ(Locale locale, SummaryDTO summaryDTO) throws IOException {
        GptPromptBody gptPromptBody = getGptPromptBody(locale);
        Map<String, String> requestBody = Map.of(
                "system_message", gptPromptBody.instruction,
                "user_message", new Gson().toJson(summaryDTO) + "\n\n" + gptPromptBody.template);

        Unirest.config().reset();
        Unirest.config().connectTimeout(1000000000);

        HttpResponse<JsonNode> response = Unirest
                .post("http://10.10.111.106:8000/prompt/jira-summary/")
                .body(requestBody)
                .asJson();

        return response.getBody().getObject().getString("result");
    }


    /* SummaryDTO를 요약한 ChatGPT 결과 리턴 (CREATED) */
    public String summaryWithCreated(Locale locale, SummaryDTO summaryDTO, Date fromDate, Date toDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "[" + simpleDateFormat.format(fromDate) + " ~ " + simpleDateFormat.format(toDate) + "]\n" + requestGPT(locale, summaryDTO);
    }


    /* SummaryDTO를 요약한 ChatGPT 결과를 리턴 (STATUS) */
    public String summaryWithStatus(Locale locale, SummaryDTO summaryDTO, String status) {
        return "[" + status + "]\n" + requestGPT(locale, summaryDTO);
    }


    @Builder
    private static class GptRequestMessage {
        private String role;
        private String content;
    }


    @Builder
    private static class GptPromptBody {
        private String instruction;
        private String template;
    }

}
