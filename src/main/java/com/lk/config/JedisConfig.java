package com.lk.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;


@Configuration
public class JedisConfig extends CachingConfigurerSupport {


    @Resource
    private RedisConfigProperty redisConfigProperty;

    /**
     * 连接池配置
     */
    @Bean(name = "jedisPoolConfig")
    @ConfigurationProperties(prefix = "spring.redis.pool-config")
    public JedisPoolConfig getRedisConfig() {
        return new JedisPoolConfig();
    }

    /**
     * jedis 连接池
     */
    @Bean(name = "jedisPool")
    public JedisPool jedisPool(@Qualifier(value = "jedisPoolConfig") final JedisPoolConfig jedisPoolConfig) {
        String host = redisConfigProperty.getHost();
        int timeout = redisConfigProperty.getTimeout();
        int port = redisConfigProperty.getPort();
        String password = redisConfigProperty.getPassword();
        int database = redisConfigProperty.getDatabase();
        return new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
    }
}
