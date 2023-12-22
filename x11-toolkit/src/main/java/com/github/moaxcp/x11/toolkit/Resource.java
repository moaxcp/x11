package com.github.moaxcp.x11.toolkit;

import lombok.Getter;

public abstract class Resource implements AutoCloseable {
  @Getter
  private int id;
  protected final Display display;

  Resource(Display display) {
    this.display = display;
    id = display.nextResourceId();
  }
}
