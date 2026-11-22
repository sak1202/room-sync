package com.roomsync.health;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/api/v1/status") public Map<String,String> status() { return Map.of("service", "roomsync-api", "status", "UP"); }
}
