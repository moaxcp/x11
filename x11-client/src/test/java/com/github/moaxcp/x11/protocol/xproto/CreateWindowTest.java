package com.github.moaxcp.x11.protocol.xproto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateWindowTest {
  @Test
  void opcode() {
    assertThat(CreateWindow.OPCODE).isEqualTo((byte) 1);
  }

  @Test
  void normalLength() {
    assertThat(CreateWindow.builder().clazz(WindowClass.COPY_FROM_PARENT).build().getLength()).isEqualTo(8);
  }

  @Test
  void withBackgroundPixel() {
    CreateWindow request = CreateWindow.builder()
      .clazz(WindowClass.COPY_FROM_PARENT)
      .backgroundPixel(10)
      .build();
    assertThat(request.isValueMaskEnabled(Cw.BACK_PIXEL));
    assertThat(request.getSize()).isEqualTo(36);
    assertThat(request.getLength()).isEqualTo(9);
  }

  @Test
  void withBackgroundPixelAndBorderPixel() {
    CreateWindow request = CreateWindow.builder()
      .clazz(WindowClass.COPY_FROM_PARENT)
      .backgroundPixel(10)
      .borderPixel(11)
      .build();
    assertThat(request.isValueMaskEnabled(Cw.BACK_PIXEL));
    assertThat(request.getSize()).isEqualTo(40);
    assertThat(request.getLength()).isEqualTo(10);
  }

  @Test
  void withEventMask() {
    CreateWindow request = CreateWindow.builder()
      .clazz(WindowClass.COPY_FROM_PARENT)
      .eventMaskEnable(EventMask.KEY_PRESS)
      .build();
    assertThat(request.isEventMaskEnabled(EventMask.KEY_PRESS)).isTrue();
    assertThat(request.isValueMaskEnabled(Cw.EVENT_MASK));
    assertThat(request.getSize()).isEqualTo(36);
    assertThat(request.getLength()).isEqualTo(9);
  }
}
