package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SADeviceBtn implements ActionUnion, XStruct, XkbObject {
  private byte type;

  private byte flags;

  private byte count;

  private byte button;

  private byte device;

  public static SADeviceBtn readSADeviceBtn(X11Input in) throws IOException {
    SADeviceBtn.SADeviceBtnBuilder javaBuilder = SADeviceBtn.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte count = in.readCard8();
    byte button = in.readCard8();
    byte device = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.count(count);
    javaBuilder.button(button);
    javaBuilder.device(device);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(count);
    out.writeCard8(button);
    out.writeCard8(device);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SADeviceBtnBuilder {
    public SADeviceBtn.SADeviceBtnBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SADeviceBtn.SADeviceBtnBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
