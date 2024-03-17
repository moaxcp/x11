package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDeviceProperty implements TwoWayRequest<GetDevicePropertyReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 39;

  private int property;

  private int type;

  private int offset;

  private int len;

  private byte deviceId;

  private boolean delete;

  public XReplyFunction<GetDevicePropertyReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDevicePropertyReply.readGetDevicePropertyReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDeviceProperty readGetDeviceProperty(X11Input in) throws IOException {
    GetDeviceProperty.GetDevicePropertyBuilder javaBuilder = GetDeviceProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int property = in.readCard32();
    int type = in.readCard32();
    int offset = in.readCard32();
    int len = in.readCard32();
    byte deviceId = in.readCard8();
    boolean delete = in.readBool();
    byte[] pad9 = in.readPad(2);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.offset(offset);
    javaBuilder.len(len);
    javaBuilder.deviceId(deviceId);
    javaBuilder.delete(delete);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard32(offset);
    out.writeCard32(len);
    out.writeCard8(deviceId);
    out.writeBool(delete);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDevicePropertyBuilder {
    public int getSize() {
      return 24;
    }
  }
}
