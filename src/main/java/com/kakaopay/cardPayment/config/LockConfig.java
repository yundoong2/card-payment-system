package com.kakaopay.cardPayment.config;

import com.kakaopay.cardPayment.common.util.LockUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class LockConfig {
    @Bean
    public LockUtil lockUtil(RedisConnectionFactory connectionFactory) {
        return new LockUtil(connectionFactory, "cardinfo");
    }
}
