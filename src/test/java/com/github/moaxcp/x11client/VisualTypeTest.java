package com.github.moaxcp.x11client;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VisualTypeTest {
  @Test
  void readVisualType() throws IOException {
    X11Input in = mock(X11Input.class);
    when(in.readCard32()).thenReturn(1, 55, 66, 77, 666);
    when(in.readCard8()).thenReturn(5, 32);
    when(in.readCard16()).thenReturn(128);
    VisualType visualType = VisualType.readVisualType(in);
    assertThat(visualType.getVisualId()).isEqualTo(1);
    assertThat(visualType.getVisualTypeClass()).isEqualTo(VisualTypeClass.DIRECT_COLOR);
    assertThat(visualType.getBitsPerRgbValue()).isEqualTo(32);
    assertThat(visualType.getColorMapEntries()).isEqualTo(128);
    assertThat(visualType.getRedMask()).isEqualTo(55);
    assertThat(visualType.getGreenMask()).isEqualTo(66);
    assertThat(visualType.getBlueMask()).isEqualTo(77);
  }
}
