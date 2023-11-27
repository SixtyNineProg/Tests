package ru.clevertec.product.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.ProductTestData;

class InMemoryProductRepositoryTest {

  private final InMemoryProductRepository inMemoryProductRepository =
      new InMemoryProductRepository();

  @BeforeEach
  public void initEach() {
    Product product = ProductTestData.builder().build().buildProduct();
    inMemoryProductRepository.save(product);
    product = ProductTestData.builder().withUuid(null).build().buildProduct();
    inMemoryProductRepository.save(product);
    product = ProductTestData.builder().withUuid(null).withName("monitor").build().buildProduct();
    inMemoryProductRepository.save(product);
  }

  @Test
  void findById_whenFindByUuid_thenOptionalProductExpected() {
    // given
    Product expected = ProductTestData.builder().build().buildProduct();

    // when
    Optional<Product> actual = inMemoryProductRepository.findById(expected.getUuid());

    // then
    assertThat(actual.orElseThrow())
        .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
        .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName());
  }

  @Test
  void findById_whenFindByNull_thenOptionalEmptyExpected() {
    // when
    Optional<Product> actual = inMemoryProductRepository.findById(null);

    // then
    assertThat(actual).isEmpty();
  }

  @Test
  void findAll_whenFindAll_thenProductExpectedSizeThree() {
    // given
    // initEach() data provide

    // when
    List<Product> actual = inMemoryProductRepository.findAll();

    // then
    assertThat(actual).isNotEmpty().hasSize(2);
  }

  @Test
  void save_whenSaveProductWithNullUuid_thenProductWithUuidExpected() {
    // given
    Product expected = ProductTestData.builder().withUuid(null).build().buildProduct();

    // when
    Product actual = inMemoryProductRepository.save(expected);

    // then
    assertThat(actual).isNotNull().extracting(Product.Fields.uuid).isNotNull();
  }

  @Test
  void delete_whenDelete_thenRemoveProduct() {
    // given
    // initEach() data provide

    // when
    inMemoryProductRepository.delete(ProductTestData.builder().build().getUuid());
    Optional<Product> deleteProduct =
        inMemoryProductRepository.findById(ProductTestData.builder().build().getUuid());

    // then
    assertThat(deleteProduct).isEmpty();
  }

  @Test
  void update_whenUpdate_thenUpdatedEntityExpected() {
    // given
    Product expected =
        ProductTestData.builder()
            .withName("updated product")
            .withDescription("updated description")
            .build()
            .buildProduct();

    // when
    inMemoryProductRepository.save(expected);
    Optional<Product> actual = inMemoryProductRepository.findById(expected.getUuid());

    // then
    assertThat(actual.orElseThrow())
        .isNotNull()
        .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
        .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription());
  }
}
