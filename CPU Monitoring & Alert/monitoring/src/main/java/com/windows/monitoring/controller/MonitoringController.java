package com.windows.monitoring.controller;

import com.arkanosis.jpdh.JPDHException;
import com.windows.monitoring.ResourceType;
import com.windows.monitoring.service.MonitoringService;
import com.windows.monitoring.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringService monitoringService;
    private final SlackService slackService;

    @GetMapping("/{type}")
    public ResponseEntity<String> printResultTemplate(@PathVariable(name = "type") ResourceType type) {
        return ResponseEntity.ok(monitoringService.printResultTemplate(type));
    }

    @GetMapping("/slack/{type}")
    public void sendAlertMessage(@PathVariable(name = "type") ResourceType type) {
        slackService.sendMessage(type);
    }

    /*
    @GetMapping("/jpdh")
    public void jpdh() throws JPDHException {
        monitoringService.jpdh();
    }
     */
}
