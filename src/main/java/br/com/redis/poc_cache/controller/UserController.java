package br.com.redis.poc_cache.controller;

import br.com.redis.poc_cache.model.User;
import br.com.redis.poc_cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(UserController.USERS)
public class UserController {

  public final static String USERS = "/users";

  @Autowired
  private UserService service;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody User user) {
    User userCreated = this.service.save(user);
    Map<String, Object> map = new HashMap<>();
    map.put("id", userCreated.getId());
    URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(USERS + "/{id}")
        .buildAndExpand(map).toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> get(@PathVariable("id") UUID id) {
    Optional<User> userOpt = this.service.getById(id);
    return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
  }

  @GetMapping
  public ResponseEntity<List<User>> getAll() {
    return ResponseEntity.ok(this.service.getAll());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
    this.service.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAll() {
    this.service.deleteAll();
    return ResponseEntity.ok().build();
  }

}
