package com.windows.monitoring.component;

import com.windows.monitoring.ResourceType;
import com.windows.monitoring.helper.MonitoringHelper;
import com.windows.monitoring.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringScheduler {

    private final MonitoringHelper monitoringHelper;
    private final SlackService slackService;

    @Scheduled(fixedDelay = 5000)
    private void monitoring() {
        double cpuUsage = monitoringHelper.getCpuUsage();
        if (cpuUsage * 100 > 10) slackService.sendMessage(ResourceType.CPU_OS);

        double processCpuUsage = monitoringHelper.getProcessCpuUsage();
        if(processCpuUsage * 100 > 10) slackService.sendMessage(ResourceType.CPU_PROCESS);
    }

}
