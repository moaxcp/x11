package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.struct.Expression.*;
import static com.github.moaxcp.x11.struct.StructBuilder.struct;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpressionTest {
  @Test
  void constant_expression() {
    var expression = constant(5);
    var struct = struct().build();
    assertThat(expression.evaluate(struct)).isEqualTo(5);
  }

  @Test
  void valueOf_expression() {
    var expression = valueOf(0);
    var struct = struct()
        .byteType()
        .build();

    struct.setByte(0, 2);

    assertThat(expression.evaluate(struct)).isEqualTo(2);
  }

  @Test
  void sum_expression() {
    var expression = sum(valueOf(0), valueOf(1), constant(1));
    var struct = struct()
        .byteType()
        .byteType()
        .build();

    struct.setByte(0, 2);
    struct.setByte(1, 3);

    assertThat(expression.evaluate(struct)).isEqualTo(6);
  }
}
