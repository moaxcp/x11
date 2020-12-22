package com.github.moaxcp.x11client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public class ResourceIdService {
  private int nextResourceId = 1;
  @Getter(AccessLevel.PACKAGE)
  private final XProtocolService protocolService;

  public ResourceIdService(@NonNull XProtocolService protocolService) {
    this.protocolService = protocolService;
  }

  public int getNextResourceId() {
    return nextResourceId++;
  }
}
