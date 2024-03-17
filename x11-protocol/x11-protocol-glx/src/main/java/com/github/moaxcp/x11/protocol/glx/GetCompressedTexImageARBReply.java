package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetCompressedTexImageARBReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int size;

  @NonNull
  private List<Byte> data;

  public static GetCompressedTexImageARBReply readGetCompressedTexImageARBReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetCompressedTexImageARBReply.GetCompressedTexImageARBReplyBuilder javaBuilder = GetCompressedTexImageARBReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(8);
    int size = in.readInt32();
    byte[] pad6 = in.readPad(12);
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.size(size);
    javaBuilder.data(data);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    int length = data.size();
    out.writeCard32(getLength());
    out.writePad(8);
    out.writeInt32(size);
    out.writePad(12);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetCompressedTexImageARBReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
