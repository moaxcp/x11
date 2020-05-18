package com.github.moaxcp.x11client;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.VisualTypeClass.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VisualTypeClassTest {
  @Test
  void staticGray() {
    assertThat(STATIC_GRAY.getCode()).isEqualTo(0);
    assertThat(VisualTypeClass.getByCode(0)).isEqualTo(STATIC_GRAY);
  }

  @Test
  void grayScale() {
    assertThat(GRAY_SCALE.getCode()).isEqualTo(1);
    assertThat(VisualTypeClass.getByCode(1)).isEqualTo(GRAY_SCALE);
  }

  @Test
  void staticColor() {
    assertThat(STATIC_COLOR.getCode()).isEqualTo(2);
    assertThat(VisualTypeClass.getByCode(2)).isEqualTo(STATIC_COLOR);
  }

  @Test
  void pseudoColor() {
    assertThat(PSEUDO_COLOR.getCode()).isEqualTo(3);
    assertThat(VisualTypeClass.getByCode(3)).isEqualTo(PSEUDO_COLOR);
  }

  @Test
  void trueColor() {
    assertThat(TRUE_COLOR.getCode()).isEqualTo(4);
    assertThat(VisualTypeClass.getByCode(4)).isEqualTo(TRUE_COLOR);
  }

  @Test
  void directColor() {
    assertThat(DIRECT_COLOR.getCode()).isEqualTo(5);
    assertThat(VisualTypeClass.getByCode(5)).isEqualTo(DIRECT_COLOR);
  }
}
