package br.com.redis.poc_cache.service;

import br.com.redis.poc_cache.cache.repository.ProductRepositoryImpl;
import br.com.redis.poc_cache.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

  private final ProductRepositoryImpl repository;

  public ProductService(ProductRepositoryImpl repository) {
    this.repository = repository;
  }

  public Product create(Product product) {
    return this.repository.save(product);
  }

  public Optional<Product> getById(Long id) {
    return this.repository.findById(id);
  }

  public void deleteById(Long id) {
    this.repository.deleteById(id);
  }
}
