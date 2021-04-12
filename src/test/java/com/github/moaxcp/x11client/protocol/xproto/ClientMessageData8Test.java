package com.github.moaxcp.x11client.protocol.xproto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientMessageData8Test {
  @Test
  void constructor_nullData() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new ClientMessageData8(null));
    assertThat(exception).hasMessage("data8 is marked non-null but is null");
  }

  @Test
  void constructor_wrong_size() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ClientMessageData8(new ArrayList<>()));
    assertThat(exception).hasMessage("data8 must have length of 20. Got: \"0\"");
  }

  @Test
  void constructor() {
    List<Byte> data8 = IntStream.range(0, 20)
        .mapToObj(i -> (byte) i)
        .collect(toList());
    ClientMessageData8 message = new ClientMessageData8(data8);
    assertThat(message.getData8()).hasSize(20);
    assertThat(message.getSize()).isEqualTo(20);
  }
}
