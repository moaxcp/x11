package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class GetPropertyReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private byte format;

  private short sequenceNumber;

  private int type;

  private int bytesAfter;

  private int valueLen;

  @NonNull
  private ByteList value;

  public static GetPropertyReply readGetPropertyReply(byte format, short sequenceNumber,
      X11Input in) throws IOException {
    GetPropertyReply.GetPropertyReplyBuilder javaBuilder = GetPropertyReply.builder();
    int length = in.readCard32();
    int type = in.readCard32();
    int bytesAfter = in.readCard32();
    int valueLen = in.readCard32();
    byte[] pad7 = in.readPad(12);
    ByteList value = in.readVoid((int) (Integer.toUnsignedLong(valueLen) * (Byte.toUnsignedInt(format) / 8)));
    javaBuilder.format(format);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.type(type);
    javaBuilder.bytesAfter(bytesAfter);
    javaBuilder.valueLen(valueLen);
    javaBuilder.value(value.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetPropertyReplyBuilder {
    public int getSize() {
      return 32 + 1 * value.size();
    }
  }
}
