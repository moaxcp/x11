package com.github.moaxcp.x11client.basicwm;

import com.github.moaxcp.x11client.XephyrRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BasicWMIT {
  private XephyrRunner runner;

  @BeforeEach
  void setup() throws IOException {
    runner = XephyrRunner.builder()
      .ac(true)
      .br(true)
      .noreset(true)
      .screen("1200x1000")
      .softCursor(true)
      .withXTerm(2)
      .build();
    runner.start();
  }

  @AfterEach
  void teardown() throws InterruptedException {
    runner.stop();
  }

  @Test
  void wm() throws IOException {
    BasicWindowManager.main(runner.getDisplay());
  }
}
