package com.github.moaxcp.x11.x11client.basicwm;

import com.github.moaxcp.x11.xephyr.XephyrRunner;

import java.io.IOException;

public class BasicWMMain {

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
    BasicWindowManager.main(runner.getDisplay());
    runner.stop();
  }
}
