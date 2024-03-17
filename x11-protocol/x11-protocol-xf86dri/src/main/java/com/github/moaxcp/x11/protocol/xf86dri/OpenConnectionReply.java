package com.github.moaxcp.x11.protocol.xf86dri;

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
public class OpenConnectionReply implements XReply {
  public static final String PLUGIN_NAME = "xf86dri";

  private short sequenceNumber;

  private int sareaHandleLow;

  private int sareaHandleHigh;

  @NonNull
  private List<Byte> busId;

  public static OpenConnectionReply readOpenConnectionReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    OpenConnectionReply.OpenConnectionReplyBuilder javaBuilder = OpenConnectionReply.builder();
    int length = in.readCard32();
    int sareaHandleLow = in.readCard32();
    int sareaHandleHigh = in.readCard32();
    int busIdLen = in.readCard32();
    byte[] pad7 = in.readPad(12);
    List<Byte> busId = in.readChar((int) (Integer.toUnsignedLong(busIdLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.sareaHandleLow(sareaHandleLow);
    javaBuilder.sareaHandleHigh(sareaHandleHigh);
    javaBuilder.busId(busId);
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
    out.writeCard32(getLength());
    out.writeCard32(sareaHandleLow);
    out.writeCard32(sareaHandleHigh);
    int busIdLen = busId.size();
    out.writeCard32(busIdLen);
    out.writePad(12);
    out.writeChar(busId);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * busId.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class OpenConnectionReplyBuilder {
    public int getSize() {
      return 32 + 1 * busId.size();
    }
  }
}
