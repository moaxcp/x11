package com.github.moaxcp.x11.toolkit;

public abstract class Resource implements AutoCloseable {
  private int id;
  protected final Display display;

  Resource(Display display) {
    this.display = display;
    id = display.nextResourceId();
  }

  public int getId() {
    return this.id;
  }
}
