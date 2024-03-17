package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIGetClientPointerReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private short sequenceNumber;

  private boolean set;

  private short deviceid;

  public static XIGetClientPointerReply readXIGetClientPointerReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    XIGetClientPointerReply.XIGetClientPointerReplyBuilder javaBuilder = XIGetClientPointerReply.builder();
    int length = in.readCard32();
    boolean set = in.readBool();
    byte[] pad5 = in.readPad(1);
    short deviceid = in.readCard16();
    byte[] pad7 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.set(set);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(set);
    out.writePad(1);
    out.writeCard16(deviceid);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIGetClientPointerReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
