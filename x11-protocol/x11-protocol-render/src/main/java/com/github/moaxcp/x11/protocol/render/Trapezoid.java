package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Trapezoid implements XStruct {
  public static final String PLUGIN_NAME = "render";

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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class TrapezoidBuilder {
    public int getSize() {
      return 40;
    }
  }
}
