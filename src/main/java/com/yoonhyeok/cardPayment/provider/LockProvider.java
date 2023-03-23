package com.yoonhyeok.cardPayment.provider;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;

import java.util.concurrent.locks.Lock;

/**
 * 동시성 제어를 위한 분산락 정의 클래스
 * @author cyh68
 * @since 2023-03-23
 **/
public class LockProvider {
    private LockRegistry lockRegistry;
    private String registryKey;
    private Lock lock;

    /**
     * 초기화를 위한 생성자
     * @param connectionFactory {@link RedisConnectionFactory}
     * @param registryKey {@link String}
     * @author cyh68
     * @since 2023-03-23
     */
    public LockProvider(RedisConnectionFactory connectionFactory, String registryKey) {
        this.registryKey = registryKey;
        this.lockRegistry = new RedisLockRegistry(connectionFactory, registryKey, 10000);
    }

    /**
     * key를 사용하여 Lock 획득 메소드
     * @param lockKey {@link String}
     * @return Lock {@link Lock}
     * @author cyh68
     * @since 2023-03-23
     **/
    public Lock lock(String lockKey) {
        this.lock = lockRegistry.obtain(lockKey);
        return this.lock;
    }

    /**
     * Lock 해제 메소드
     * @author cyh68
     * @since 2023-03-23
     **/
    public void unlock() {
        this.lock.unlock();
    }
}
