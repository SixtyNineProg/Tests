package util.provider;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import util.ProductTestData;

public class ArgumentsProviderMerge implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(
        Arguments.of(
            ProductTestData.builder().build().buildProduct(),
            ProductTestData.builder().withDescription("hello").build().buildProductDto(),
            ProductTestData.builder().withDescription("hello").build().buildProduct()),
        Arguments.of(
            ProductTestData.builder().withName("phone").build().buildProduct(),
            ProductTestData.builder().withName("keyboard").build().buildProductDto(),
            ProductTestData.builder().withName("keyboard").build().buildProduct()));
  }
}
