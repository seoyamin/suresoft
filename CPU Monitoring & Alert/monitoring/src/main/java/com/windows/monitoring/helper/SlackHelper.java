package com.windows.monitoring.helper;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import com.windows.monitoring.ResourceType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class SlackHelper {

    @Value("${slack.webhook-url}")
    private String webhookUrl;
    private final Slack slack = Slack.getInstance();


    public boolean sendAlertMessage(ResourceType type, String value) {
        try {
            Payload payload = Payload.builder()
                    .text("[" + type.getDescription() + "] " + value + "%")
                    .build();

            WebhookResponse webhookResponse = slack.send(webhookUrl, payload);
            System.out.println(webhookResponse);
        } catch (IOException e) {
            System.out.println("문제 발생 : " + e.getMessage());
        }

        return true;
    }
}
