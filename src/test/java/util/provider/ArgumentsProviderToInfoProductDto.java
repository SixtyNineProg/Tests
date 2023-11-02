package util.provider;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import util.ProductTestData;

public class ArgumentsProviderToInfoProductDto implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(
        Arguments.of(
            ProductTestData.builder().build().buildProduct(),
            ProductTestData.builder().build().buildInfoProductDto()),
        Arguments.of(
            ProductTestData.builder().build().buildProduct(),
            ProductTestData.builder().build().buildInfoProductDto()));
  }
}
