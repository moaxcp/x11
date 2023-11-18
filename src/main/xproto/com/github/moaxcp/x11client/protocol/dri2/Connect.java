package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Connect implements TwoWayRequest<ConnectReply>, Dri2Object {
  public static final byte OPCODE = 1;

  private int window;

  private int driverType;

  public XReplyFunction<ConnectReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ConnectReply.readConnectReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Connect readConnect(X11Input in) throws IOException {
    Connect.ConnectBuilder javaBuilder = Connect.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int driverType = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.driverType(driverType);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(driverType);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class ConnectBuilder {
    public Connect.ConnectBuilder driverType(DriverType driverType) {
      this.driverType = (int) driverType.getValue();
      return this;
    }

    public Connect.ConnectBuilder driverType(int driverType) {
      this.driverType = driverType;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
