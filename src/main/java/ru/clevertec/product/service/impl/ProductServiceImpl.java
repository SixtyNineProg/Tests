package ru.clevertec.product.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductMapper mapper;
  private final ProductRepository productRepository;

  @Override
  public InfoProductDto get(UUID uuid) {
    return null;
  }

  @Override
  public List<InfoProductDto> getAll() {
    return null;
  }

  @Override
  public UUID create(ProductDto productDto) {
    return null;
  }

  @Override
  public void update(UUID uuid, ProductDto productDto) {}

  @Override
  public void delete(UUID uuid) {}
}
