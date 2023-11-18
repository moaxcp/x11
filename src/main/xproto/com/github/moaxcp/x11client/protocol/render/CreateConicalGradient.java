package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateConicalGradient implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 36;

  private int picture;

  @NonNull
  private Pointfix center;

  private int angle;

  @NonNull
  private List<Integer> stops;

  @NonNull
  private List<Color> colors;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateConicalGradient readCreateConicalGradient(X11Input in) throws IOException {
    CreateConicalGradient.CreateConicalGradientBuilder javaBuilder = CreateConicalGradient.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int picture = in.readCard32();
    Pointfix center = Pointfix.readPointfix(in);
    int angle = in.readInt32();
    int numStops = in.readCard32();
    List<Integer> stops = in.readInt32((int) (Integer.toUnsignedLong(numStops)));
    List<Color> colors = new ArrayList<>((int) (Integer.toUnsignedLong(numStops)));
    for(int i = 0; i < Integer.toUnsignedLong(numStops); i++) {
      colors.add(Color.readColor(in));
    }
    javaBuilder.picture(picture);
    javaBuilder.center(center);
    javaBuilder.angle(angle);
    javaBuilder.stops(stops);
    javaBuilder.colors(colors);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    center.write(out);
    out.writeInt32(angle);
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
    return 24 + 4 * stops.size() + XObject.sizeOf(colors);
  }

  public static class CreateConicalGradientBuilder {
    public int getSize() {
      return 24 + 4 * stops.size() + XObject.sizeOf(colors);
    }
  }
}
