package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AddMaster implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private boolean sendCore;

  private boolean enable;

  @NonNull
  private List<Byte> name;

  public static AddMaster readAddMaster(X11Input in) throws IOException {
    AddMaster.AddMasterBuilder javaBuilder = AddMaster.builder();
    short type = in.readCard16();
    short len = in.readCard16();
    short nameLen = in.readCard16();
    boolean sendCore = in.readBool();
    boolean enable = in.readBool();
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameLen));
    in.readPadAlign(Short.toUnsignedInt(nameLen));
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.sendCore(sendCore);
    javaBuilder.enable(enable);
    javaBuilder.name(name);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writeBool(sendCore);
    out.writeBool(enable);
    out.writeChar(name);
    out.writePadAlign(Short.toUnsignedInt(nameLen));
  }

  @Override
  public int getSize() {
    return 8 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AddMasterBuilder {
    public AddMaster.AddMasterBuilder type(HierarchyChangeType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public AddMaster.AddMasterBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
    }
  }
}
