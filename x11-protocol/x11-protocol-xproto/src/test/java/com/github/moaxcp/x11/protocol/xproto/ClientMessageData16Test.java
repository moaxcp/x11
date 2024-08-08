package com.github.moaxcp.x11.protocol.xproto;

import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.junit.jupiter.api.Test;

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
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ClientMessageData16(ShortLists.immutable.empty()));
    assertThat(exception).hasMessage("data16 must have length of 10. Got: \"0\"");
  }

  @Test
  void constructor() {
    var data16 = org.eclipse.collections.api.factory.primitive.ShortLists.mutable.withInitialCapacity(10);
    for (int i = 0; i < 10; i++) {
      data16.add((byte) i);
    }
    ClientMessageData16 message = new ClientMessageData16(data16);
    assertThat(message.getData16().size()).isEqualTo(10);
    assertThat(message.getSize()).isEqualTo(20);
  }
}
