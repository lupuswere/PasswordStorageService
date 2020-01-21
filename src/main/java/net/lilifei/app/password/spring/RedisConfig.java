package net.lilifei.app.password.spring;

import org.springframework.cloud.aws.cache.config.annotation.CacheClusterConfig;
import org.springframework.cloud.aws.cache.config.annotation.EnableElastiCache;

@EnableElastiCache({@CacheClusterConfig(name = "spring-session")})
public class RedisConfig {
}
