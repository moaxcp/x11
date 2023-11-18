package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.IntValue;
import java.util.HashMap;
import java.util.Map;

public enum PictOp implements IntValue {
  CLEAR(0),

  SRC(1),

  DST(2),

  OVER(3),

  OVER_REVERSE(4),

  IN(5),

  IN_REVERSE(6),

  OUT(7),

  OUT_REVERSE(8),

  ATOP(9),

  ATOP_REVERSE(10),

  XOR(11),

  ADD(12),

  SATURATE(13),

  DISJOINT_CLEAR(16),

  DISJOINT_SRC(17),

  DISJOINT_DST(18),

  DISJOINT_OVER(19),

  DISJOINT_OVER_REVERSE(20),

  DISJOINT_IN(21),

  DISJOINT_IN_REVERSE(22),

  DISJOINT_OUT(23),

  DISJOINT_OUT_REVERSE(24),

  DISJOINT_ATOP(25),

  DISJOINT_ATOP_REVERSE(26),

  DISJOINT_XOR(27),

  CONJOINT_CLEAR(32),

  CONJOINT_SRC(33),

  CONJOINT_DST(34),

  CONJOINT_OVER(35),

  CONJOINT_OVER_REVERSE(36),

  CONJOINT_IN(37),

  CONJOINT_IN_REVERSE(38),

  CONJOINT_OUT(39),

  CONJOINT_OUT_REVERSE(40),

  CONJOINT_ATOP(41),

  CONJOINT_ATOP_REVERSE(42),

  CONJOINT_XOR(43),

  MULTIPLY(48),

  SCREEN(49),

  OVERLAY(50),

  DARKEN(51),

  LIGHTEN(52),

  COLOR_DODGE(53),

  COLOR_BURN(54),

  HARD_LIGHT(55),

  SOFT_LIGHT(56),

  DIFFERENCE(57),

  EXCLUSION(58),

  H_S_L_HUE(59),

  H_S_L_SATURATION(60),

  H_S_L_COLOR(61),

  H_S_L_LUMINOSITY(62);

  static final Map<Integer, PictOp> byCode = new HashMap<>();

  static {
        for(PictOp e : values()) {
            byCode.put(e.value, e);
        }
  }

  private int value;

  PictOp(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  public static PictOp getByCode(int code) {
    return byCode.get(code);
  }
}
