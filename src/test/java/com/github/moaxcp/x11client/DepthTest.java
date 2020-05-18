package com.github.moaxcp.x11client;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DepthTest {
  @Test
  void readDepth() throws IOException {
    X11Input in = mock(X11Input.class);
    given(in.readCard8()).willReturn(32, 0, 5, 32);
    given(in.readCard16()).willReturn(1, 128);
    given(in.readCard32()).willReturn( 1, 55, 66, 77, 666);

    Depth depth = Depth.readDepth(in);
    assertThat(depth.getDepth()).isEqualTo(32);
    assertThat(depth.getVisualTypes()).hasSize(1);
    VisualType visualType = depth.getVisualTypes().get(0);
    assertThat(visualType.getVisualId()).isEqualTo(1);
    assertThat(visualType.getVisualTypeClass()).isEqualTo(VisualTypeClass.DIRECT_COLOR);
    assertThat(visualType.getBitsPerRgbValue()).isEqualTo(32);
    assertThat(visualType.getColorMapEntries()).isEqualTo(128);
    assertThat(visualType.getRedMask()).isEqualTo(55);
    assertThat(visualType.getGreenMask()).isEqualTo(66);
    assertThat(visualType.getBlueMask()).isEqualTo(77);
  }
}
