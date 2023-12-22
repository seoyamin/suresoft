package com.windows.monitoring.helper;

import com.arkanosis.jpdh.Counter;
import com.arkanosis.jpdh.JPDH;
import com.arkanosis.jpdh.JPDHException;
import com.arkanosis.jpdh.Query;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Component
public class MonitoringHelper {

    public double getCpuUsage() {
        OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osMXBean.getCpuLoad();
    }

    public double getProcessCpuUsage() {
        Date ddd = new Date();
        Calendar ccc = Calendar.getInstance(Locale.CANADA);

        OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osMXBean.getProcessCpuLoad();
    }

    public long getTotalMemorySize() {
        OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osMXBean.getTotalMemorySize();
    }

    public long getFreeMemorySize() {
        OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osMXBean.getFreeMemorySize();
    }

    /*
    public void jpdh() throws JPDHException {
        try(Query query = JPDH.openQuery()) {
            Counter diskCounter = query.addCounter("\\PhysicalDisk(_Total)\\Disk Read Bytes/sec");
            Counter cpuCounter = query.addCounter("\\PID(672)\\% User Time");
            query.collectData();
            System.out.println("Disk Counter = " + diskCounter.getDoubleValue());
            System.out.println("CPU Counter = " + cpuCounter.getIntegerValue());
            query.removeCounter(diskCounter);
        }
    }
     */
}
