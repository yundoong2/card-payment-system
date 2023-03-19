package com.kakaopay.cardPayment.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;


@Component
@RequiredArgsConstructor
public class LockUtil {
    private LockRegistry lockRegistry;
    private String registryKey;
    private Lock lock;

    public LockUtil(RedisConnectionFactory connectionFactory, String registryKey) {
        this.registryKey = registryKey;
        this.lockRegistry = new RedisLockRegistry(connectionFactory, registryKey, 10000);
    }

    public Lock lock(String lockKey) {
        this.lock = lockRegistry.obtain(lockKey);
        return this.lock;
    }

    public void unlock() {
        this.lock.unlock();
    }
}
