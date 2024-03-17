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
public class ListInputDevices implements TwoWayRequest<ListInputDevicesReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 2;

  public XReplyFunction<ListInputDevicesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListInputDevicesReply.readListInputDevicesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListInputDevices readListInputDevices(X11Input in) throws IOException {
    ListInputDevices.ListInputDevicesBuilder javaBuilder = ListInputDevices.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListInputDevicesBuilder {
    public int getSize() {
      return 4;
    }
  }
}
