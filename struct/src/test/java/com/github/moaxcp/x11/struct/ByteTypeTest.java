package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteTypeTest {
  @Test
  void constructor() {
    var schema = new ByteType(15);
    assertThat(schema.getPosition()).isEqualTo(15);
  }
}
