package br.com.redis.poc_cache.service;

import br.com.redis.poc_cache.cache.repository.PersonRepositoryImpl;
import br.com.redis.poc_cache.model.Person;
import br.com.redis.poc_cache.model.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

  private final PersonRepositoryImpl repository;

  public PersonService(PersonRepositoryImpl repository) {
    this.repository = repository;
  }

  public Person create(Person person) {
    return this.repository.save(person);
  }

  public Optional<Person> getById(UUID id) {
    return this.repository.findById(id);
  }

  public void deleteById(UUID id) {
    this.repository.deleteById(id);
  }

  public void deleteAll() {
    this.repository.deleteAll();
  }
}
