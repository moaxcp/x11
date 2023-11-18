package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetPictureFilter implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 30;

  private int picture;

  @NonNull
  private List<Byte> filter;

  @NonNull
  private List<Integer> values;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPictureFilter readSetPictureFilter(X11Input in) throws IOException {
    SetPictureFilter.SetPictureFilterBuilder javaBuilder = SetPictureFilter.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int picture = in.readCard32();
    javaStart += 4;
    short filterLen = in.readCard16();
    javaStart += 2;
    byte[] pad5 = in.readPad(2);
    javaStart += 2;
    List<Byte> filter = in.readChar(Short.toUnsignedInt(filterLen));
    javaStart += 1 * filter.size();
    in.readPadAlign(Short.toUnsignedInt(filterLen));
    javaStart += XObject.getSizeForPadAlign(4, 1 * filter.size());
    List<Integer> values = in.readInt32(javaStart - length);
    javaBuilder.picture(picture);
    javaBuilder.filter(filter);
    javaBuilder.values(values);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    short filterLen = (short) filter.size();
    out.writeCard16(filterLen);
    out.writePad(2);
    out.writeChar(filter);
    out.writePadAlign(Short.toUnsignedInt(filterLen));
    out.writeInt32(values);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * filter.size() + XObject.getSizeForPadAlign(4, 1 * filter.size()) + 4 * values.size();
  }

  public static class SetPictureFilterBuilder {
    public int getSize() {
      return 12 + 1 * filter.size() + XObject.getSizeForPadAlign(4, 1 * filter.size()) + 4 * values.size();
    }
  }
}
