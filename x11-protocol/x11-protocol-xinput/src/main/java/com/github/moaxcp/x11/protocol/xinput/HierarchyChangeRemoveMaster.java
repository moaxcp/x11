package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HierarchyChangeRemoveMaster implements HierarchyChange {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private short deviceid;

  private byte returnMode;

  private short returnPointer;

  private short returnKeyboard;

  public static HierarchyChangeRemoveMaster readHierarchyChangeRemoveMaster(short type, short len,
      X11Input in) throws IOException {
    HierarchyChangeRemoveMaster.HierarchyChangeRemoveMasterBuilder javaBuilder = HierarchyChangeRemoveMaster.builder();
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class HierarchyChangeRemoveMasterBuilder {
    public HierarchyChangeRemoveMaster.HierarchyChangeRemoveMasterBuilder type(
        HierarchyChangeType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public HierarchyChangeRemoveMaster.HierarchyChangeRemoveMasterBuilder type(short type) {
      this.type = type;
      return this;
    }

    public HierarchyChangeRemoveMaster.HierarchyChangeRemoveMasterBuilder returnMode(
        ChangeMode returnMode) {
      this.returnMode = (byte) returnMode.getValue();
      return this;
    }

    public HierarchyChangeRemoveMaster.HierarchyChangeRemoveMasterBuilder returnMode(
        byte returnMode) {
      this.returnMode = returnMode;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
