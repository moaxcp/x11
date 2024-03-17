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
public class XIGetFocus implements TwoWayRequest<XIGetFocusReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 50;

  private short deviceid;

  public XReplyFunction<XIGetFocusReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIGetFocusReply.readXIGetFocusReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIGetFocus readXIGetFocus(X11Input in) throws IOException {
    XIGetFocus.XIGetFocusBuilder javaBuilder = XIGetFocus.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceid = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIGetFocusBuilder {
    public int getSize() {
      return 8;
    }
  }
}
