package com.windows.monitoring.service;

import com.windows.monitoring.ResourceType;
import com.windows.monitoring.helper.SlackHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackHelper slackHelper;
    private final MonitoringService monitoringService;

    public void sendMessage(ResourceType type) {
        String percentStr = monitoringService.getPercentStr(type);

        if(slackHelper.sendAlertMessage(type, percentStr)) {
            System.out.println("메시지가 전송되었습니다.");
        };
    }

}
