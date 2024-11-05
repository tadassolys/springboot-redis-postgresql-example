package com.example.redis_postgresql_demo.config;

import com.example.redis_postgresql_demo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * Configures a RedisTemplate for interacting with Redis.
     * The template is set up with custom serializers for keys and values.
     *
     * @param redisConnectionFactory the factory for creating Redis connections
     * @return a configured RedisTemplate for String keys and User values
     */
    @Bean
    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // Create a new RedisTemplate for handling Redis operations
        RedisTemplate<String, User> template = new RedisTemplate<>();
        
        // Set the Redis connection factory that enables the template to connect to Redis
        template.setConnectionFactory(redisConnectionFactory);

        // Use StringRedisSerializer to serialize the Redis keys as plain strings
        template.setKeySerializer(new StringRedisSerializer());

        // Use GenericJackson2JsonRedisSerializer to serialize the values as JSON,
        // allowing complex User objects to be stored and retrieved easily
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    // other data examples
    /*
    @Bean
    public RedisTemplate<String, Product> productRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Product> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> sessionRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
    */
}
