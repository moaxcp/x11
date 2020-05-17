package com.github.moaxcp.x11client;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class FormatTest {
  @Test
  void readFormat() throws IOException {
    X11Input in = mock(X11Input.class);
    when(in.readCard8()).thenReturn(16, 32, 128);
    Format format = Format.readFormat(in);
    assertThat(format.getDepth()).isEqualTo(16);
    assertThat(format.getBitsPerPixel()).isEqualTo(32);
    assertThat(format.getScanlinePad()).isEqualTo(128);
    then(in).should(times(3)).readCard8();
    then(in).should().readString8(5);
  }
}
