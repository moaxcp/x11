package com.github.moaxcp.x11client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ResourceIdServiceWithoutXCMisc {
  @Mock
  XProtocolService protocolService;
  ResourceIdService service;

  @BeforeEach
  void setup() {
    service = new ResourceIdService(protocolService);
  }

  @Test
  void firstIdIs1() {
    int id = service.getNextResourceId();
    assertThat(id).isEqualTo(1);
  }

  @Test
  void secondIdIs2() {
    service.getNextResourceId();
    int id = service.getNextResourceId();
    assertThat(id).isEqualTo(2);
  }
}
