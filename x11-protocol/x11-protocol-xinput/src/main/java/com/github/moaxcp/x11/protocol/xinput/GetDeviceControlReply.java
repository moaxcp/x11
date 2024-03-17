package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetDeviceControlReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  private byte status;

  @NonNull
  private DeviceState control;

  public static GetDeviceControlReply readGetDeviceControlReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    GetDeviceControlReply.GetDeviceControlReplyBuilder javaBuilder = GetDeviceControlReply.builder();
    int length = in.readCard32();
    byte status = in.readCard8();
    byte[] pad5 = in.readPad(23);
    DeviceState control = DeviceState.readDeviceState(in);
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.status(status);
    javaBuilder.control(control);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(status);
    out.writePad(23);
    control.write(out);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceControlReplyBuilder {
    public int getSize() {
      return 36;
    }
  }
}
