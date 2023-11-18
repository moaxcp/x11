package com.github.moaxcp.x11client.protocol.glx;

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
public class GetPolygonStippleReply implements XReply, GlxObject {
  private short sequenceNumber;

  @NonNull
  private List<Byte> data;

  public static GetPolygonStippleReply readGetPolygonStippleReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetPolygonStippleReply.GetPolygonStippleReplyBuilder javaBuilder = GetPolygonStippleReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.sequenceNumber(sequenceNumber);
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
    out.writePad(24);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
  }

  public static class GetPolygonStippleReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
