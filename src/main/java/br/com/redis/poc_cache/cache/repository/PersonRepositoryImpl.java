package br.com.redis.poc_cache.cache.repository;

import br.com.redis.poc_cache.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class PersonRepositoryImpl
    implements CrudRepository<Person, UUID>, ComposeKeys<UUID, String> {

  public static final String PEOPLE = "people";
  @Autowired
  @Qualifier("redisTemplatePeople")
  private RedisTemplate<String, Person> redisTemplate;

  @Override
  public Person save(Person person) {
    this.redisTemplate.opsForValue()
        .set(this.composeKeys(person.getId()), person, person.getExpiration(), TimeUnit.SECONDS);
    return person;
  }

  @Override
  public Optional<Person> findById(UUID id) {
    Person person = this.redisTemplate.opsForValue().get(this.composeKeys(id));
    return Optional.ofNullable(person);
  }

  @Override
  public void deleteById(UUID id) {
    this.redisTemplate.delete(this.composeKeys(id));
  }

  public void deleteAll() {
    Set<String> keys = this.redisTemplate.keys(String.format("%s:*", PEOPLE));
    if (Objects.nonNull(keys)) {
      Long count = this.redisTemplate.delete(keys);
      System.out.printf("%d people deleted!\n", count);
    }
  }

  @Override
  public String composeKeys(UUID key) {
    return String.format("%s:%s", PEOPLE, key);
  }

}
