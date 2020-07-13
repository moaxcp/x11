package com.github.moaxcp.x11client;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ScreenTest {
  @Test
  void readScreen() throws IOException {
    X11Input in = mock(X11Input.class);
    given(in.readCard32()).willReturn(1);

    Screen screen = Screen.readScreen(in);
    assertThat(screen.getRoot()).isEqualTo(747);
    assertThat(screen.getDefaultColorMap()).isEqualTo(32);
    assertThat(screen.getDefaultWhitePixel()).isEqualTo(16777215);
    assertThat(screen.getDefaultBlackPixel()).isEqualTo(0);
    assertThat(screen.getCurrentInputMasks()).isEqualTo(16416831);
    assertThat(screen.getWidth()).isEqualTo(1920);
    assertThat(screen.getHeight()).isEqualTo(1080);
    assertThat(screen.getWidthInMillimeters()).isEqualTo(341);
    assertThat(screen.getHeightInMillimeters()).isEqualTo(190);
    assertThat(screen.getMinInstalledMaps()).isEqualTo(0);
    assertThat(screen.getMaxInstalledMaps()).isEqualTo(0);
    assertThat(screen.getRootVisualId()).isEqualTo(65537);
    assertThat(screen.getBackingStore()).isEqualTo(BackingStore.ALWAYS);
    assertThat(screen.isSaveUnders()).isFalse();
    assertThat(screen.getRootDepth()).isEqualTo(0);
    assertThat(screen.getAllowedDepths()).hasSize(1);
  }
}
