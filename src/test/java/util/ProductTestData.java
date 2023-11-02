package util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
@Accessors(chain = true)
public class ProductTestData {

  @Builder.Default private UUID uuid = UUID.fromString("097ceff8-27a8-4b31-a019-5069ea80ab5b");

  @Builder.Default private String name = "laptop";

  @Builder.Default private String description = "work laptop";

  @Builder.Default private BigDecimal price = BigDecimal.valueOf(10000);

  @Builder.Default private LocalDateTime created = LocalDateTime.of(2023, 10, 31, 9, 0);

  public InfoProductDto buildInfoProductDto() {
    return InfoProductDto.builder()
        .uuid(uuid)
        .name(name)
        .description(description)
        .price(price)
        .build();
  }

  public Product buildProduct() {
    return Product.builder()
        .uuid(uuid)
        .name(name)
        .created(created)
        .description(description)
        .price(price)
        .build();
  }

  public ProductDto buildProductDto() {
    return ProductDto.builder().name(name).description(description).price(price).build();
  }
}
