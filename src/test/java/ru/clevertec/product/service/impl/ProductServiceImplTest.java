package ru.clevertec.product.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ProductTestData;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock private ProductMapper mapper;
  @Mock private ProductRepository productRepository;

  @InjectMocks private ProductServiceImpl productServiceImpl;

  @Captor private ArgumentCaptor<UUID> uuidArgumentCaptor;
  @Captor private ArgumentCaptor<Product> productArgumentCaptor;

  @Test
  void testGet_whenGetByUuid_thenProductExpected() {
    // given
    ProductTestData expected = ProductTestData.builder().build();
    Product product = ProductTestData.builder().build().buildProduct();
    InfoProductDto productMapperTestData = ProductTestData.builder().build().buildInfoProductDto();
    Optional<Product> optionalProductRepository = Optional.ofNullable(product);
    doReturn(optionalProductRepository).when(productRepository).findById(expected.getUuid());
    doReturn(productMapperTestData)
        .when(mapper)
        .toInfoProductDto(optionalProductRepository.orElseThrow());

    // when
    InfoProductDto actual = productServiceImpl.get(expected.getUuid());

    // then
    assertThat(actual)
        .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
        .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription());
  }

  @Test
  void testGet_whenGetByUuid_thenCallOneTimeRepositoryFindByExpected() {
    // given
    ProductTestData expected = ProductTestData.builder().build();
    Product product = ProductTestData.builder().build().buildProduct();
    InfoProductDto productMapperTestData = ProductTestData.builder().build().buildInfoProductDto();
    Optional<Product> optionalProductRepository = Optional.ofNullable(product);
    doReturn(optionalProductRepository).when(productRepository).findById(expected.getUuid());
    doReturn(productMapperTestData)
        .when(mapper)
        .toInfoProductDto(optionalProductRepository.orElseThrow());

    // when
    productServiceImpl.get(expected.getUuid());

    // then
    verify(productRepository).findById(expected.getUuid());
  }

  @Test
  void testGet_whenGetByUnknownUuid_thenProductNotFoundExceptionExpected() {
    // given
    UUID uuid = ProductTestData.builder().build().getUuid();
    doReturn(Optional.empty()).when(productRepository).findById(uuid);

    // when
    ProductNotFoundException thrown =
        Assertions.assertThrows(ProductNotFoundException.class, () -> productServiceImpl.get(uuid));

    // then
    assertThat(thrown).hasMessage(String.format("Product with uuid: %s not found", uuid));
  }

  @Test
  void testGet_whenGetByUuid_thenCallRepositoryWithThisUuid() {
    // given
    ProductTestData expected = ProductTestData.builder().build();
    Product product = ProductTestData.builder().build().buildProduct();
    InfoProductDto productMapperTestData = ProductTestData.builder().build().buildInfoProductDto();
    Optional<Product> optionalProductRepository = Optional.ofNullable(product);
    doReturn(optionalProductRepository).when(productRepository).findById(expected.getUuid());
    doReturn(productMapperTestData)
        .when(mapper)
        .toInfoProductDto(optionalProductRepository.orElseThrow());

    // when
    productServiceImpl.get(expected.getUuid());

    // then
    verify(productRepository).findById(uuidArgumentCaptor.capture());
    assertThat(uuidArgumentCaptor.getValue()).isEqualByComparingTo(expected.getUuid());
  }

  @Test
  void testGetAll_whenGetAll_thenGetNonEmptyList() {
    // given
    List<InfoProductDto> listInfoProductDto =
        ProductTestData.builder().build().buildListInfoProductDto();
    List<Product> productRepositoryTestData = ProductTestData.builder().build().buildListProducts();
    doReturn(productRepositoryTestData).when(productRepository).findAll();
    doReturn(listInfoProductDto).when(mapper).toListInfoProductDto(productRepositoryTestData);

    // when
    List<InfoProductDto> actual = productServiceImpl.getAll();

    // then
    assertThat(actual).isNotEmpty();
  }

  @Test
  void testGetAll_whenGetByUuid_thenCallOneTimeRepositoryFindAllExpected() {
    // given
    List<InfoProductDto> listInfoProductDto =
        ProductTestData.builder().build().buildListInfoProductDto();
    List<Product> productRepositoryTestData = ProductTestData.builder().build().buildListProducts();
    doReturn(productRepositoryTestData).when(productRepository).findAll();
    doReturn(listInfoProductDto).when(mapper).toListInfoProductDto(productRepositoryTestData);

    // when
    productServiceImpl.getAll();

    // then
    verify(productRepository).findAll();
  }

  @Test
  void testCreate_whenCreate_theUuidExpected() {
    // given
    ProductDto productDto = ProductTestData.builder().build().buildProductDto();
    Product product = ProductTestData.builder().build().buildProduct();
    InfoProductDto productMapperTestData = ProductTestData.builder().build().buildInfoProductDto();
    doReturn(product).when(mapper).toProduct(productDto);
    doReturn(product).when(productRepository).save(product);
    doReturn(productMapperTestData).when(mapper).toInfoProductDto(product);
    UUID expected = ProductTestData.builder().build().getUuid();

    // when
    UUID actual = productServiceImpl.create(productDto);

    // then
    assertThat(actual).isEqualByComparingTo(expected);
  }

  @Test
  void testCreate_whenCreate_thenCallRepositoryWithExpectedProduct() {
    // given
    ProductDto expected = ProductTestData.builder().build().buildProductDto();
    Product product = ProductTestData.builder().build().buildProduct();
    InfoProductDto productMapperTestData = ProductTestData.builder().build().buildInfoProductDto();
    doReturn(product).when(mapper).toProduct(expected);
    doReturn(product).when(productRepository).save(product);
    doReturn(productMapperTestData).when(mapper).toInfoProductDto(product);

    // when
    productServiceImpl.create(expected);

    // then
    verify(productRepository).save(productArgumentCaptor.capture());
    assertThat(uuidArgumentCaptor.getValue())
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.name())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.price())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.description())
        .hasFieldOrPropertyWithValue(Product.Fields.uuid, null)
        .extracting(Product.Fields.created)
        .isNotNull();
  }

  @Test
  void testUpdate_whenUpdate_thenCallRepositorySaveWithExpectedProduct() {
    // given
    ProductDto expected = ProductTestData.builder().build().buildProductDto();
    Product product = ProductTestData.builder().build().buildProduct();
    UUID uuid = product.getUuid();
    Optional<Product> optionalProduct = Optional.of(product);
    doReturn(product).when(mapper).toProduct(expected);
    doReturn(optionalProduct).when(productRepository).findById(uuid);
    doReturn(product).when(productRepository).save(product);

    // when
    productServiceImpl.update(uuid, expected);

    // then
    verify(productRepository).save(productArgumentCaptor.capture());
    assertThat(uuidArgumentCaptor.getValue())
        .hasFieldOrPropertyWithValue(Product.Fields.uuid, uuid)
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.name())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.price())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.description())
        .extracting(Product.Fields.created)
        .isNotNull();
  }

  @Test
  void testDelete_whenDelete_thenCallRepositoryDeleteWithThisUuid() {
    // given
    UUID uuid = ProductTestData.builder().build().getUuid();
    doNothing().when(productRepository).delete(uuid);

    // when
    productServiceImpl.delete(uuid);

    // then
    verify(productRepository).delete(uuidArgumentCaptor.capture());
    assertThat(uuidArgumentCaptor.getValue()).isEqualByComparingTo(uuid);
  }
}
