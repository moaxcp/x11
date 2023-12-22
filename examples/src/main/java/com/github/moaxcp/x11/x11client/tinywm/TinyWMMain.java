package com.github.moaxcp.x11.x11client.tinywm;

import com.github.moaxcp.x11.xephyr.XephyrRunner;

import java.io.IOException;

public class TinyWMMain {
  public static void main(String... args) throws IOException, InterruptedException {
    XephyrRunner runner = XephyrRunner.builder()
            .ac(true)
            .br(true)
            .noreset(true)
            .screen("1200x1000")
            .softCursor(true)
            .withXTerm(2)
            .build();
    runner.start();
    new TinyWindowManager(runner.getDisplay()).start();
    runner.stop();
  }
}
