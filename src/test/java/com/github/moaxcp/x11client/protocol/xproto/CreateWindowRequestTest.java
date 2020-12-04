package com.github.moaxcp.x11client.protocol.xproto;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.protocol.xproto.EventMaskEnum.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateWindowRequestTest {
  @Test
  void opcode() {
    assertThat(CreateWindowRequest.OPCODE).isEqualTo((byte) 1);
  }

  @Test
  void normalLength() {
    assertThat(CreateWindowRequest.builder().clazz(WindowClassEnum.COPY_FROM_PARENT).build().getLength()).isEqualTo(8);
  }

  @Test
  void withBackgroundPixel() {
    CreateWindowRequest request = CreateWindowRequest.builder()
      .clazz(WindowClassEnum.COPY_FROM_PARENT)
      .backgroundPixel(10)
      .build();
    assertThat(request.isValueMaskEnabled(CwEnum.BACK_PIXEL));
    assertThat(request.getSize()).isEqualTo(36);
    assertThat(request.getLength()).isEqualTo(9);
  }

  @Test
  void withBackgroundPixelAndBorderPixel() {
    CreateWindowRequest request = CreateWindowRequest.builder()
      .clazz(WindowClassEnum.COPY_FROM_PARENT)
      .backgroundPixel(10)
      .borderPixel(11)
      .build();
    assertThat(request.isValueMaskEnabled(CwEnum.BACK_PIXEL));
    assertThat(request.getSize()).isEqualTo(40);
    assertThat(request.getLength()).isEqualTo(10);
  }

  @Test
  void withEventMask() {
    CreateWindowRequest request = CreateWindowRequest.builder()
      .clazz(WindowClassEnum.COPY_FROM_PARENT)
      .eventMaskEnable(KEY_PRESS)
      .build();
    assertThat(request.isEventMaskEnabled(KEY_PRESS)).isTrue();
    assertThat(request.isValueMaskEnabled(CwEnum.EVENT_MASK));
    assertThat(request.getSize()).isEqualTo(36);
    assertThat(request.getLength()).isEqualTo(9);
  }
}
