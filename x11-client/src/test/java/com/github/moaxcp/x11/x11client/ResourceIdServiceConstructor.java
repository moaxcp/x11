package com.github.moaxcp.x11.x11client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class ResourceIdServiceConstructor {
  @Test
  void constructor_nullService() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new ResourceIdService(null, 0b011, 0b100));
    assertThat(exception).hasMessage("protocolService");
  }

  @Test
  void constructor() {
    ResourceIdService service = new ResourceIdService(mock(XProtocolService.class), 0b011, 0b100);
    assertThat(service.getNextResourceId()).isEqualTo(1);
  }
}
