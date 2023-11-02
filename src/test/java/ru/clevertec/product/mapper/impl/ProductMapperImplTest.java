package ru.clevertec.product.mapper.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import util.ProductTestData;
import util.provider.ArgumentsProviderMerge;
import util.provider.ArgumentsProviderToInfoProductDto;
import util.provider.ArgumentsProviderToProduct;

class ProductMapperImplTest {

  private final ProductMapperImpl productMapper = new ProductMapperImpl();

  @ParameterizedTest
  @ArgumentsSource(ArgumentsProviderToProduct.class)
  void testToProduct_whenToProductProduct_thenProductDtoExpected(
      ProductDto productDto, Product expected) {
    // Given
    // ProductDto and expected Product are provided by the MethodSource

    // When
    Product actual = productMapper.toProduct(productDto);

    // Then
    assertThat(actual)
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
  }

  @Test
  void testToProduct_whenToProductWithNullProductDto_thenProductNotFoundExceptionExpected() {
    // given
    ProductDto productDto = null;

    // when
    ProductNotFoundException thrown =
        Assertions.assertThrows(
            ProductNotFoundException.class, () -> productMapper.toProduct(productDto));
    // then
    assertThat(thrown).hasMessage(ProductNotFoundException.DEFAULT_MESSAGE);
  }

  @ParameterizedTest
  @ArgumentsSource(ArgumentsProviderToInfoProductDto.class)
  void testToInfoProductDto_whenToInfoProductDto_thenInfoProductDtoExpected(
      Product product, InfoProductDto expected) {
    // Given
    // Product and expected InfoProductDto are provided by the MethodSource

    // When
    InfoProductDto actual = productMapper.toInfoProductDto(product);

    // Then
    assertThat(actual)
        .hasFieldOrPropertyWithValue(InfoProductDto.Fields.uuid, expected.uuid())
        .hasFieldOrPropertyWithValue(InfoProductDto.Fields.name, expected.name())
        .hasFieldOrPropertyWithValue(InfoProductDto.Fields.description, expected.description())
        .hasFieldOrPropertyWithValue(InfoProductDto.Fields.price, expected.price());
  }

  @Test
  void
      testToInfoProductDto_whenToInfoProductDtoWithNullProduct_thenProductNotFoundExceptionExpected() {
    // given
    Product product = null;

    // when
    ProductNotFoundException thrown =
        Assertions.assertThrows(
            ProductNotFoundException.class, () -> productMapper.toInfoProductDto(product));
    // then
    assertThat(thrown).hasMessage(ProductNotFoundException.DEFAULT_MESSAGE);
  }

  @ParameterizedTest
  @ArgumentsSource(ArgumentsProviderMerge.class)
  void testMerge_whenMerge_thenProductExpected(
      Product product, ProductDto productDto, Product expected) {
    // Given
    // Product and expected InfoProductDto are provided by the MethodSource

    // When
    Product actual = productMapper.merge(product, productDto);

    // Then
    assertThat(actual)
        .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
        .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
  }

  @Test
  void testMerge_whenMergeWithNullProduct_thenProductNotFoundExceptionExpected() {
    // given
    Product product = null;
    ProductDto productDto = ProductTestData.builder().build().buildProductDto();

    // when
    ProductNotFoundException thrown =
        Assertions.assertThrows(
            ProductNotFoundException.class, () -> productMapper.merge(product, productDto));
    // then
    assertThat(thrown).hasMessage(ProductNotFoundException.DEFAULT_MESSAGE);
  }
}
