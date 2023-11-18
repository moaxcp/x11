package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HierarchyChangeAddMaster implements HierarchyChange, XinputObject {
  private short type;

  private short len;

  private boolean sendCore;

  private boolean enable;

  @NonNull
  private List<Byte> name;

  public static HierarchyChangeAddMaster readHierarchyChangeAddMaster(short type, short len,
      X11Input in) throws IOException {
    HierarchyChangeAddMaster.HierarchyChangeAddMasterBuilder javaBuilder = HierarchyChangeAddMaster.builder();
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

  public static class HierarchyChangeAddMasterBuilder {
    public HierarchyChangeAddMaster.HierarchyChangeAddMasterBuilder type(HierarchyChangeType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public HierarchyChangeAddMaster.HierarchyChangeAddMasterBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
    }
  }
}
