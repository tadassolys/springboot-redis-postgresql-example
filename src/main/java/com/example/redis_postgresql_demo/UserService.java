package com.example.redis_postgresql_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> redisTemplate;

    // Prefix for user keys in Redis to easily identify and organize user data
    private static final String USER_KEY = "USER";

    @Autowired
    public UserService(UserRepository userRepository, RedisTemplate<String, User> redisTemplate) {
        // Injecting dependencies for the User repository and Redis template
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Saves a User object to the PostgreSQL database and stores it in Redis cache.
     * @param user the User object to be saved.
     * @return the saved User object.
     */
    public User saveUser(User user) {
        // Save the user to PostgreSQL using JPA repository
        User savedUser = userRepository.save(user);

        // Cache the saved user in Redis with a unique key (combining USER_KEY and ID)
        redisTemplate.opsForValue().set(USER_KEY + savedUser.getId(), savedUser);

        // Return the saved User object (now available in both PostgreSQL and Redis)
        return savedUser;
    }

    /**
     * Retrieves a User by ID, first checking in Redis cache, then in PostgreSQL if not found.
     * @param id the ID of the user to be retrieved.
     * @return an Optional containing the User if found, or empty if not found.
     */
    public Optional<User> getUserById(Long id) {
        // Attempt to retrieve the User from Redis cache
        User user = redisTemplate.opsForValue().get(USER_KEY + id);

        // If user is found in Redis cache, return it wrapped in an Optional
        if (user != null) {
            return Optional.of(user);
        }

        // If user is not found in Redis, retrieve it from PostgreSQL database
        return userRepository.findById(id);
    }
}
