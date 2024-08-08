package com.github.moaxcp.x11.protocol.xproto;

import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.junit.jupiter.api.Test;

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
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ClientMessageData8(ByteLists.immutable.empty()));
    assertThat(exception).hasMessage("data8 must have length of 20. Got: \"0\"");
  }

  @Test
  void constructor() {
    var data8 = org.eclipse.collections.api.factory.primitive.ByteLists.mutable.withInitialCapacity(20);
    for (int i = 0; i < 20; i++) {
      data8.add((byte) i);
    }
    ClientMessageData8 message = new ClientMessageData8(data8);
    assertThat(message.getData8().size()).isEqualTo(20);
    assertThat(message.getSize()).isEqualTo(20);
  }
}
