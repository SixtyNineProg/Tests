package ru.clevertec.product.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.ProductTestData;

class InMemoryProductRepositoryTest {

  private final InMemoryProductRepository inMemoryProductRepository =
      new InMemoryProductRepository();

  @Test
  void findById_whenFindByUuid_thenOptionalProductExpected() {
    // given
    UUID uuid = ProductTestData.builder().build().getUuid();

    // when
    Optional<Product> actual = inMemoryProductRepository.findById(uuid);

    // then
    assertThat(actual).isNotEmpty();
    assertThat(actual.get()).hasFieldOrPropertyWithValue(Product.Fields.uuid, uuid);
  }

  @Test
  void findById_whenFindByNull_thenOptionalEmptyExpected() {
    // given
    UUID uuid = null;

    // when
    Optional<Product> actual = inMemoryProductRepository.findById(uuid);

    // then
    assertThat(actual).isEmpty();
  }

  @Test
  void findAll_whenFindAll_thenNotEmptyListProductExpected() {
    // given

    // when
    List<Product> actual = inMemoryProductRepository.findAll();

    // then
    assertThat(actual).isNotEmpty();
  }

  @Test
  void save_whenSaveProductWithNullUuid_thenProductWithUuidExpected() {
    // given
    Product expected = ProductTestData.builder().withUuid(null).build().buildProduct();

    // when
    Product actual = inMemoryProductRepository.save(expected);

    // then
    assertThat(actual).isNotNull().hasNoNullFieldsOrProperties();
  }

  @Test
  void delete_givenProductObject_whenDelete_thenRemoveProduct() {
    // given
    Product product = ProductTestData.builder().withUuid(null).build().buildProduct();
    inMemoryProductRepository.save(product);

    // when
    inMemoryProductRepository.delete(product.getUuid());
    Optional<Product> deleteProduct = inMemoryProductRepository.findById(product.getUuid());

    // then
    assertThat(deleteProduct).isEmpty();
  }
}
