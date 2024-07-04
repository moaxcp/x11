package com.github.moaxcp.x11.protocol.xproto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientMessageData16Test {
  @Test
  void constructor_nullData() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new ClientMessageData16(null));
    assertThat(exception).hasMessage("data16 is marked non-null but is null");
  }

  @Test
  void constructor_wrong_size() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ClientMessageData16(new ArrayList<>()));
    assertThat(exception).hasMessage("data16 must have length of 10. Got: \"0\"");
  }

  @Test
  void constructor() {
    List<Short> data16 = IntStream.range(0, 10)
        .mapToObj(i -> (short) i)
        .collect(toList());
    ClientMessageData16 message = new ClientMessageData16(data16);
    assertThat(message.getData16()).hasSize(10);
    assertThat(message.getSize()).isEqualTo(20);
  }
}
