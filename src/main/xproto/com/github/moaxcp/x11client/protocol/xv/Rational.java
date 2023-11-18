package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Rational implements XStruct, XvObject {
  private int numerator;

  private int denominator;

  public static Rational readRational(X11Input in) throws IOException {
    Rational.RationalBuilder javaBuilder = Rational.builder();
    int numerator = in.readInt32();
    int denominator = in.readInt32();
    javaBuilder.numerator(numerator);
    javaBuilder.denominator(denominator);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(numerator);
    out.writeInt32(denominator);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class RationalBuilder {
    public int getSize() {
      return 8;
    }
  }
}
