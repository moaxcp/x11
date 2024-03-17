package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class XIChangeHierarchy implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 43;

  @NonNull
  private List<HierarchyChange> changes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIChangeHierarchy readXIChangeHierarchy(X11Input in) throws IOException {
    XIChangeHierarchy.XIChangeHierarchyBuilder javaBuilder = XIChangeHierarchy.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte numChanges = in.readCard8();
    byte[] pad4 = in.readPad(3);
    List<HierarchyChange> changes = new ArrayList<>(Byte.toUnsignedInt(numChanges));
    for(int i = 0; i < Byte.toUnsignedInt(numChanges); i++) {
      changes.add(HierarchyChange.readHierarchyChange(in));
    }
    javaBuilder.changes(changes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    byte numChanges = (byte) changes.size();
    out.writeCard8(numChanges);
    out.writePad(3);
    for(HierarchyChange t : changes) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(changes);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIChangeHierarchyBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(changes);
    }
  }
}
