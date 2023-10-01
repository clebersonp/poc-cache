package br.com.redis.poc_cache.config;

import br.com.redis.poc_cache.model.Person;
import br.com.redis.poc_cache.model.Product;
import br.com.redis.poc_cache.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableRedisRepositories(basePackages = { "br.com.redis.poc_cache.cache_jpa.repository" })
@EnableCaching // for caching annotations
public class RedisConfig {

  private static ObjectMapper getObjectMapperJava8Module() {
    ObjectMapper om = new ObjectMapper();
    om.registerModule(new JavaTimeModule());
    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return om;
  }

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName("127.0.0.1");
    config.setPort(6379);
    //    RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
    //    clusterConfig.clusterNode("127.0.0.1", 6379);
    //    clusterConfig.setUsername("");
    //    clusterConfig.setUsername("");
    return new JedisConnectionFactory(config);
  }

  public <K, V> RedisTemplate<K, V> createRedisTemplateFactory(Class<K> keyType,
      Class<V> valueType) {
    Jackson2JsonRedisSerializer<?> jacksonSerializer = new Jackson2JsonRedisSerializer<>(valueType);
    jacksonSerializer.setObjectMapper(getObjectMapperJava8Module());

    GenericJackson2JsonRedisSerializer genericSerializer = new GenericJackson2JsonRedisSerializer(
        getObjectMapperJava8Module());

    RedisTemplate<K, V> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(jacksonSerializer);
    template.setHashValueSerializer(jacksonSerializer);
    template.setEnableTransactionSupport(true);
    template.afterPropertiesSet();
    return template;
  }

  @Bean("redisTemplateProducts")
  public RedisTemplate<String, Product> redisTemplateProducts() {
    return createRedisTemplateFactory(String.class, Product.class);
  }

  @Bean("redisTemplatePeople")
  public RedisTemplate<String, Person> redisTemplatePeople() {
    return createRedisTemplateFactory(String.class, Person.class);
  }

  // For caching annotations
  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return (builder) -> builder.withCacheConfiguration(User.USERS,
        myDefaultCacheConfig(User.class, Duration.ofDays(30)));
  }

  private <T> RedisCacheConfiguration myDefaultCacheConfig(Class<T> classType, Duration duration) {
    Jackson2JsonRedisSerializer<?> jacksonSerializer = new Jackson2JsonRedisSerializer<>(classType);
    jacksonSerializer.setObjectMapper(getObjectMapperJava8Module());

    return RedisCacheConfiguration.defaultCacheConfig().entryTtl(duration)
        .disableCachingNullValues().serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer))
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
            new StringRedisSerializer()));
  }

}
