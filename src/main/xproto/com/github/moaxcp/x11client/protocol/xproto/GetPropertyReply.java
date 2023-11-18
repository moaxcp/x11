package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetPropertyReply implements XReply, XprotoObject {
  private byte format;

  private short sequenceNumber;

  private int type;

  private int bytesAfter;

  private int valueLen;

  @NonNull
  private List<Byte> value;

  public static GetPropertyReply readGetPropertyReply(byte format, short sequenceNumber,
      X11Input in) throws IOException {
    GetPropertyReply.GetPropertyReplyBuilder javaBuilder = GetPropertyReply.builder();
    int length = in.readCard32();
    int type = in.readCard32();
    int bytesAfter = in.readCard32();
    int valueLen = in.readCard32();
    byte[] pad7 = in.readPad(12);
    List<Byte> value = in.readVoid((int) (Integer.toUnsignedLong(valueLen) * (Byte.toUnsignedInt(format) / 8)));
    javaBuilder.format(format);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.type(type);
    javaBuilder.bytesAfter(bytesAfter);
    javaBuilder.valueLen(valueLen);
    javaBuilder.value(value);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(format);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(type);
    out.writeCard32(bytesAfter);
    out.writeCard32(valueLen);
    out.writePad(12);
    out.writeVoid(value);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * value.size();
  }

  public static class GetPropertyReplyBuilder {
    public int getSize() {
      return 32 + 1 * value.size();
    }
  }
}
