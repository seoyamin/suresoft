package com.windows.monitoring;

import lombok.Getter;

@Getter
public enum ResourceType {

    CPU_OS("CPU Usage"),
    CPU_PROCESS("CPU Usage of Process");

    private final String description;

    ResourceType(String description) {
        this.description = description;
    }
}
