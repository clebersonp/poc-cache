package br.com.redis.poc_cache.service.annotations;

import br.com.redis.poc_cache.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserAnnotationService {

  @CachePut(cacheNames = User.USERS, key = "#user.id")
  public User save(User user) {
    user.setId(UUID.randomUUID());
    log.info("Caching the user data");
    return user;
  }

  @Cacheable(cacheNames = User.USERS, key = "#id", unless = "#result == null")
  public Optional<User> getById(UUID id) {
    log.info("Get user from cache");
    return Optional.empty();
  }

  @CacheEvict(cacheNames = User.USERS, key = "#id")
  public void deleteById(UUID id) {
  }

  @CacheEvict(cacheNames = User.USERS, allEntries = true)
  public void deleteAll() {
  }

}
