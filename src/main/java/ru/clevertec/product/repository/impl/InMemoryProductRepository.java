package ru.clevertec.product.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

public class InMemoryProductRepository implements ProductRepository {

  @Override
  public Optional<Product> findById(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public List<Product> findAll() {
    return null;
  }

  @Override
  public Product save(Product product) {
    return null;
  }

  @Override
  public void delete(UUID uuid) {}
}
