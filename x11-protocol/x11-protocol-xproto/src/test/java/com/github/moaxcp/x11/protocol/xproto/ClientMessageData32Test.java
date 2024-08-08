package com.github.moaxcp.x11.protocol.xproto;

import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientMessageData32Test {
  @Test
  void constructor_list_nullData() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new ClientMessageData32((IntList) null));
    assertThat(exception).hasMessage("data32 is marked non-null but is null");
  }

  @Test
  void constructor_list_wrong_size() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ClientMessageData32(IntLists.immutable.empty()));
    assertThat(exception).hasMessage("data32 must have length of 5. Got: \"0\"");
  }

  @Test
  void constructor_list() {
    var data32 = org.eclipse.collections.api.factory.primitive.IntLists.mutable.withInitialCapacity(5);
    for (int i = 0; i < 5; i++) {
      data32.add((byte) i);
    }
    ClientMessageData32 message = new ClientMessageData32(data32);
    assertThat(message.getData32().size()).isEqualTo(5);
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
    assertThat(message.getData32()).isEqualTo(IntLists.immutable.of(0, 1, 2, 0, 0));
  }

  @Test
  void constructor_vararg() {
    ClientMessageData32 message = new ClientMessageData32(0, 1, 2, 3, 4);
    assertThat(message.getData32()).isEqualTo(IntLists.immutable.of(0, 1, 2, 3, 4));
    assertThat(message.getData32().size()).isEqualTo(5);
    assertThat(message.getSize()).isEqualTo(20);
  }
}
