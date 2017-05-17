package org.fomky.ratpack.core.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ratpack.session.SessionModule;
import ratpack.session.store.RedisSessionModule;

import java.time.Duration;

/**
 * Session 共享处理 --
 * @author Created by Fomky on 2017/4/1412:35.
 */
@Configuration
public class SupportModules {
    @Value("${session.redis.host}")
    private String session_redis_host;
    @Value("${session.redis.port}")
    private Integer session_redis_port;

    @Bean
    public SessionModule sessionModule() {
        SessionModule sessionModule = new SessionModule();
        //.Session 过期时间 1 天
        sessionModule.configure(sessionCookieConfig -> sessionCookieConfig.expires(Duration.ofDays(1)));
        return sessionModule;
    }

    @Bean
    public RedisSessionModule redisSessionModule() {
        RedisSessionModule redisSessionModule = new RedisSessionModule();
        redisSessionModule.configure(config -> {
            config.setHost(session_redis_host);
            config.setPort(session_redis_port);
        });
        return redisSessionModule;
    }
}
