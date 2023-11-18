package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeDrawableAttributes implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 30;

  private int drawable;

  @NonNull
  private List<Integer> attribs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeDrawableAttributes readChangeDrawableAttributes(X11Input in) throws
      IOException {
    ChangeDrawableAttributes.ChangeDrawableAttributesBuilder javaBuilder = ChangeDrawableAttributes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int numAttribs = in.readCard32();
    List<Integer> attribs = in.readCard32((int) (Integer.toUnsignedLong(numAttribs) * 2));
    javaBuilder.drawable(drawable);
    javaBuilder.attribs(attribs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    int numAttribs = attribs.size();
    out.writeCard32(numAttribs);
    out.writeCard32(attribs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * attribs.size();
  }

  public static class ChangeDrawableAttributesBuilder {
    public int getSize() {
      return 12 + 4 * attribs.size();
    }
  }
}
