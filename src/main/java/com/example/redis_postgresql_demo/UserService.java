package com.example.redis_postgresql_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> redisTemplate;

    private static final String USER_KEY = "USER";

    @Autowired
    public UserService(UserRepository userRepository, RedisTemplate<String, User> redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        redisTemplate.opsForValue().set(USER_KEY + savedUser.getId(), savedUser);
        return savedUser;
    }

    public Optional<User> getUserById(Long id) {
        User user = redisTemplate.opsForValue().get(USER_KEY + id);
        if (user != null) {
            return Optional.of(user);
        }
        return userRepository.findById(id);
    }
}

