package com.yoonhyeok.cardPayment.config;

import com.yoonhyeok.cardPayment.provider.LockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 분산락 사용을 위한 Config
 * @author cyh68
 * @since 2023-03-23
 **/
@Configuration
public class LockConfig {
    
    /**
     * LockUtil 생성자 주입
     * @param connectionFactory {@link RedisConnectionFactory}
     * @return LockUtil {@link LockProvider}
     * @author cyh68
     * @since 2023-03-23
     **/
    @Bean
    public LockProvider lockUtil(RedisConnectionFactory connectionFactory) {
        return new LockProvider(connectionFactory, "info");
    }
}
