package br.com.redis.poc_cache.controller;

import br.com.redis.poc_cache.model.Person;
import br.com.redis.poc_cache.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(PersonController.PEOPLE)
public class PersonController {

  public final static String PEOPLE = "/people";

  private final PersonService service;

  public PersonController(PersonService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody Person person) {
    person.setId(UUID.randomUUID());
    Person personCreated = this.service.create(person);
    Map<String, Object> map = new HashMap<>();
    map.put("id", personCreated.getId());
    URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(PEOPLE + "/{id}")
        .buildAndExpand(map).toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Person> get(@PathVariable("id") UUID id) {
    Optional<Person> personOpt = this.service.getById(id);
    return personOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
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
