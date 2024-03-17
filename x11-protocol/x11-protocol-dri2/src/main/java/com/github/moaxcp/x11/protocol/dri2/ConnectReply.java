package com.github.moaxcp.x11.protocol.dri2;

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
public class ConnectReply implements XReply {
  public static final String PLUGIN_NAME = "dri2";

  private short sequenceNumber;

  @NonNull
  private List<Byte> driverName;

  @NonNull
  private List<Byte> alignmentPad;

  @NonNull
  private List<Byte> deviceName;

  public static ConnectReply readConnectReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    ConnectReply.ConnectReplyBuilder javaBuilder = ConnectReply.builder();
    int length = in.readCard32();
    int driverNameLength = in.readCard32();
    int deviceNameLength = in.readCard32();
    byte[] pad6 = in.readPad(16);
    List<Byte> driverName = in.readChar((int) (Integer.toUnsignedLong(driverNameLength)));
    List<Byte> alignmentPad = in.readVoid((int) ((Integer.toUnsignedLong(driverNameLength) + 3) & (~ (3)) - Integer.toUnsignedLong(driverNameLength)));
    List<Byte> deviceName = in.readChar((int) (Integer.toUnsignedLong(deviceNameLength)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.driverName(driverName);
    javaBuilder.alignmentPad(alignmentPad);
    javaBuilder.deviceName(deviceName);
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
    int driverNameLength = driverName.size();
    out.writeCard32(driverNameLength);
    int deviceNameLength = deviceName.size();
    out.writeCard32(deviceNameLength);
    out.writePad(16);
    out.writeChar(driverName);
    out.writeVoid(alignmentPad);
    out.writeChar(deviceName);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * driverName.size() + 1 * alignmentPad.size() + 1 * deviceName.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ConnectReplyBuilder {
    public int getSize() {
      return 32 + 1 * driverName.size() + 1 * alignmentPad.size() + 1 * deviceName.size();
    }
  }
}
