package com.github.moaxcp.x11client.display;

import lombok.Getter;

public abstract class Resource implements AutoCloseable {
  @Getter
  private int id;
  final Display display;

  Resource(Display display) {
    this.display = display;
    id = display.nextResourceId();
  }
}
