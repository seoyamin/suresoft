package com.windows.monitoring.service;

import com.arkanosis.jpdh.JPDHException;
import com.windows.monitoring.ResourceType;
import com.windows.monitoring.helper.MonitoringHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final MonitoringHelper monitoringHelper;

    private String templateToPrint(ResourceType type, String percentStr) {
        return new StringBuilder()
                .append("[").append(new Date()).append("] ")
                .append(type.getDescription()).append(" = ")
                .append(percentStr).append("%")
                .toString();
    }

    public String getPercentStr(ResourceType type) {
        switch (type) {
            case CPU_OS -> {
                return toPercentStr(monitoringHelper.getCpuUsage());
            }
            case CPU_PROCESS -> {
                return toPercentStr(monitoringHelper.getProcessCpuUsage());
            }
        }
        return null;
    }

    public String printResultTemplate(ResourceType type) {
        return templateToPrint(type, getPercentStr(type));
    }

    private String toPercentStr(double d) {
        return String.format("%.2f", d * 100);
    }

    /*
    public void jpdh() throws JPDHException {
        monitoringHelper.jpdh();
    }
     */
}
