package com.github.moaxcp.x11client.protocol.xproto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientMessageData32Test {
  @Test
  void constructor_list_nullData() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new ClientMessageData32((List<Integer>) null));
    assertThat(exception).hasMessage("data32 is marked non-null but is null");
  }

  @Test
  void constructor_list_wrong_size() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ClientMessageData32(new ArrayList<>()));
    assertThat(exception).hasMessage("data32 must have length of 5. Got: \"0\"");
  }

  @Test
  void constructor_list() {
    List<Integer> data32 = IntStream.range(0, 5)
        .mapToObj(i -> i)
        .collect(toList());
    ClientMessageData32 message = new ClientMessageData32(data32);
    assertThat(message.getData32()).hasSize(5);
    assertThat(message.getSize()).isEqualTo(20);
  }

  @Test
  void constructor_vararg_long() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ClientMessageData32(0, 1, 2, 3, 4, 5));
    assertThat(exception).hasMessage("data32 must have length < 20 bytes. Got: \"6\"");
  }

  @Test
  void constructor_vararg_pad() {
    ClientMessageData32 message = new ClientMessageData32(0, 1, 2);
    assertThat(message.getData32()).containsExactly(0, 1, 2, 0, 0);
  }

  @Test
  void constructor_vararg() {
    ClientMessageData32 message = new ClientMessageData32(0, 1, 2, 3, 4);
    assertThat(message.getData32()).containsExactly(0, 1, 2, 3, 4);
    assertThat(message.getData32()).hasSize(5);
    assertThat(message.getSize()).isEqualTo(20);
  }
}
