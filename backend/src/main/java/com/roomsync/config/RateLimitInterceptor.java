package com.roomsync.config;

import java.time.Duration;
import java.util.List;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Distributed fixed-window limiter. At the edge, also apply gateway/WAF limits. */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate redis;
    public RateLimitInterceptor(StringRedisTemplate redis) { this.redis = redis; }
    @Override public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String ip = req.getRemoteAddr(); // Replace with trusted gateway-derived client IP in production.
        String key = "rl:v1:" + ip + ":" + (System.currentTimeMillis() / 60_000);
        Long count = redis.opsForValue().increment(key);
        if (count != null && count == 1) redis.expire(key, Duration.ofMinutes(2));
        if (count != null && count > 120) {
            res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            res.setHeader("Retry-After", "60");
            res.setContentType("application/json");
            res.getWriter().write("{\"code\":\"RATE_LIMITED\",\"message\":\"Too many requests\"}");
            return false;
        }
        return true;
    }
}
