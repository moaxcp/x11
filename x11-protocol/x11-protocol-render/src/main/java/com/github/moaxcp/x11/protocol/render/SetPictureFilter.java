package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetPictureFilter implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

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
    byte majorOpcode = in.readCard8();
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
    List<Integer> values = in.readInt32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.picture(picture);
    javaBuilder.filter(filter);
    javaBuilder.values(values);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetPictureFilterBuilder {
    public int getSize() {
      return 12 + 1 * filter.size() + XObject.getSizeForPadAlign(4, 1 * filter.size()) + 4 * values.size();
    }
  }
}
