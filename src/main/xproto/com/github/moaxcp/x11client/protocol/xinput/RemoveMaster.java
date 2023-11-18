package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RemoveMaster implements XStruct, XinputObject {
  private short type;

  private short len;

  private short deviceid;

  private byte returnMode;

  private short returnPointer;

  private short returnKeyboard;

  public static RemoveMaster readRemoveMaster(X11Input in) throws IOException {
    RemoveMaster.RemoveMasterBuilder javaBuilder = RemoveMaster.builder();
    short type = in.readCard16();
    short len = in.readCard16();
    short deviceid = in.readCard16();
    byte returnMode = in.readCard8();
    byte[] pad4 = in.readPad(1);
    short returnPointer = in.readCard16();
    short returnKeyboard = in.readCard16();
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.deviceid(deviceid);
    javaBuilder.returnMode(returnMode);
    javaBuilder.returnPointer(returnPointer);
    javaBuilder.returnKeyboard(returnKeyboard);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(deviceid);
    out.writeCard8(returnMode);
    out.writePad(1);
    out.writeCard16(returnPointer);
    out.writeCard16(returnKeyboard);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class RemoveMasterBuilder {
    public RemoveMaster.RemoveMasterBuilder type(HierarchyChangeType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public RemoveMaster.RemoveMasterBuilder type(short type) {
      this.type = type;
      return this;
    }

    public RemoveMaster.RemoveMasterBuilder returnMode(ChangeMode returnMode) {
      this.returnMode = (byte) returnMode.getValue();
      return this;
    }

    public RemoveMaster.RemoveMasterBuilder returnMode(byte returnMode) {
      this.returnMode = returnMode;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
