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
                .append(percentStr)
                .toString();
    }

    public String getPercentStr(ResourceType type) {
        switch (type) {
            case CPU_OS -> {
                return toPercentStr(monitoringHelper.getCpuUsage()) + "%";
            }
            case CPU_PROCESS -> {
                return toPercentStr(monitoringHelper.getProcessCpuUsage()) + "%";
            }
            case MEMORY_TOTAL -> {
                return toBytesStr(monitoringHelper.getTotalMemorySize()) + " GB";
            }
            case MEMORY_FREE -> {
                return toBytesStr(monitoringHelper.getFreeMemorySize()) + " GB";
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

    private String toBytesStr(double l) {
        return String.format("%.2f", l/1000000000);
    }

    /*
    public void jpdh() throws JPDHException {
        monitoringHelper.jpdh();
    }
     */
}
