package ru.clevertec.product.data;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder
@FieldNameConstants
public record ProductDto(

    /** {@link ru.clevertec.product.entity.Product} */
    String name,

    /** {@link ru.clevertec.product.entity.Product} */
    String description,

    /** {@link ru.clevertec.product.entity.Product} */
    BigDecimal price) {}
