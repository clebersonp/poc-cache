package br.com.redis.poc_cache.service;

import br.com.redis.poc_cache.cache_jpa.repository.UserRepository;
import br.com.redis.poc_cache.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public User save(User user) {
    return this.repository.save(user);
  }

  public Optional<User> getById(UUID id) {
    return this.repository.findById(id);
  }

  public List<User> getAll() {
    return StreamSupport.stream(this.repository.findAll().spliterator(), false)
        .filter(Objects::nonNull).collect(Collectors.toList());
  }

  public void deleteById(UUID id) {
    this.repository.deleteById(id);
  }

  public void deleteAll() {
    this.repository.deleteAll();
  }

}
