package br.com.redis.poc_cache.cache.repository;

import br.com.redis.poc_cache.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class ProductRepositoryImpl
    implements CrudRepository<Product, Long>, ComposeKeys<Long, String> {

  public static final String PRODUCTS = "products";
  @Autowired
  @Qualifier("redisTemplateProducts")
  private RedisTemplate<String, Product> redisTemplate;

  @Override
  public Product save(Product product) {
    this.redisTemplate.opsForValue()
        .set(this.composeKeys(product.getId()), product, product.getExpiration(), TimeUnit.SECONDS);
    return product;
  }

  @Override
  public Optional<Product> findById(Long id) {
    Product product = this.redisTemplate.opsForValue().get(this.composeKeys(id));
    return Optional.ofNullable(product);
  }

  @Override
  public void deleteById(Long id) {
    this.redisTemplate.opsForValue().getAndDelete(this.composeKeys(id));
  }

  @Override
  public String composeKeys(Long key) {
    return String.format("%s:%d", PRODUCTS, key);
  }
}
