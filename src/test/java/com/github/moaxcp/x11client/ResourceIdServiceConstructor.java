package com.github.moaxcp.x11client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class ResourceIdServiceConstructor {
  @Test
  void constructor_nullService() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new ResourceIdService(null));
    assertThat(exception).hasMessage("protocolService is marked non-null but is null");
  }

  @Test
  void constructor() {
    XProtocolService protocol = mock(XProtocolService.class);
    ResourceIdService resourceIdService = new ResourceIdService(protocol);
    assertThat(resourceIdService.getProtocolService()).isEqualTo(protocol);
  }
}
