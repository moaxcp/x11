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
public class GetDeviceKeyMapping implements TwoWayRequest<GetDeviceKeyMappingReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 24;

  private byte deviceId;

  private byte firstKeycode;

  private byte count;

  public XReplyFunction<GetDeviceKeyMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDeviceKeyMappingReply.readGetDeviceKeyMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDeviceKeyMapping readGetDeviceKeyMapping(X11Input in) throws IOException {
    GetDeviceKeyMapping.GetDeviceKeyMappingBuilder javaBuilder = GetDeviceKeyMapping.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte deviceId = in.readCard8();
    byte firstKeycode = in.readCard8();
    byte count = in.readCard8();
    byte[] pad6 = in.readPad(1);
    javaBuilder.deviceId(deviceId);
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.count(count);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(deviceId);
    out.writeCard8(firstKeycode);
    out.writeCard8(count);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceKeyMappingBuilder {
    public int getSize() {
      return 8;
    }
  }
}
