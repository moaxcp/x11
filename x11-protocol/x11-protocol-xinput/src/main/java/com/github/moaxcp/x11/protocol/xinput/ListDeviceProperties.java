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
public class ListDeviceProperties implements TwoWayRequest<ListDevicePropertiesReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 36;

  private byte deviceId;

  public XReplyFunction<ListDevicePropertiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListDevicePropertiesReply.readListDevicePropertiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListDeviceProperties readListDeviceProperties(X11Input in) throws IOException {
    ListDeviceProperties.ListDevicePropertiesBuilder javaBuilder = ListDeviceProperties.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte deviceId = in.readCard8();
    byte[] pad4 = in.readPad(3);
    javaBuilder.deviceId(deviceId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(deviceId);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListDevicePropertiesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
