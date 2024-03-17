package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateRadialGradient implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 35;

  private int picture;

  @NonNull
  private Pointfix inner;

  @NonNull
  private Pointfix outer;

  private int innerRadius;

  private int outerRadius;

  @NonNull
  private List<Integer> stops;

  @NonNull
  private List<Color> colors;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRadialGradient readCreateRadialGradient(X11Input in) throws IOException {
    CreateRadialGradient.CreateRadialGradientBuilder javaBuilder = CreateRadialGradient.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    Pointfix inner = Pointfix.readPointfix(in);
    Pointfix outer = Pointfix.readPointfix(in);
    int innerRadius = in.readInt32();
    int outerRadius = in.readInt32();
    int numStops = in.readCard32();
    List<Integer> stops = in.readInt32((int) (Integer.toUnsignedLong(numStops)));
    List<Color> colors = new ArrayList<>((int) (Integer.toUnsignedLong(numStops)));
    for(int i = 0; i < Integer.toUnsignedLong(numStops); i++) {
      colors.add(Color.readColor(in));
    }
    javaBuilder.picture(picture);
    javaBuilder.inner(inner);
    javaBuilder.outer(outer);
    javaBuilder.innerRadius(innerRadius);
    javaBuilder.outerRadius(outerRadius);
    javaBuilder.stops(stops);
    javaBuilder.colors(colors);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    inner.write(out);
    outer.write(out);
    out.writeInt32(innerRadius);
    out.writeInt32(outerRadius);
    int numStops = colors.size();
    out.writeCard32(numStops);
    out.writeInt32(stops);
    for(Color t : colors) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 36 + 4 * stops.size() + XObject.sizeOf(colors);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateRadialGradientBuilder {
    public int getSize() {
      return 36 + 4 * stops.size() + XObject.sizeOf(colors);
    }
  }
}
