package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Connect implements TwoWayRequest<ConnectReply> {
  public static final String PLUGIN_NAME = "dri2";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int driverType = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.driverType(driverType);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(driverType);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
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
