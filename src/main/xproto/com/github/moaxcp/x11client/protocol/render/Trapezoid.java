package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Trapezoid implements XStruct, RenderObject {
  private int top;

  private int bottom;

  @NonNull
  private Linefix left;

  @NonNull
  private Linefix right;

  public static Trapezoid readTrapezoid(X11Input in) throws IOException {
    Trapezoid.TrapezoidBuilder javaBuilder = Trapezoid.builder();
    int top = in.readInt32();
    int bottom = in.readInt32();
    Linefix left = Linefix.readLinefix(in);
    Linefix right = Linefix.readLinefix(in);
    javaBuilder.top(top);
    javaBuilder.bottom(bottom);
    javaBuilder.left(left);
    javaBuilder.right(right);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(top);
    out.writeInt32(bottom);
    left.write(out);
    right.write(out);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public static class TrapezoidBuilder {
    public int getSize() {
      return 40;
    }
  }
}
