package com.github.moaxcp.x11client;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class X11ConnectionIT {
  @Test
  void test() throws IOException {
    try(X11Connection connection = X11Connection.connect()) {

    }
  }
}
