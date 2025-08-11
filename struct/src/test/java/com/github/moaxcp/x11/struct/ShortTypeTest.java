package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortTypeTest {
  @Test
  void constructor() {
    var schema = new ShortType(15);
    assertThat(schema.getPosition()).isEqualTo(15);
  }
}
