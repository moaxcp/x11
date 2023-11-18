package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class XIChangeHierarchy implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 43;

  @NonNull
  private List<HierarchyChange> changes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIChangeHierarchy readXIChangeHierarchy(X11Input in) throws IOException {
    XIChangeHierarchy.XIChangeHierarchyBuilder javaBuilder = XIChangeHierarchy.builder();
    byte numChanges = in.readCard8();
    short length = in.readCard16();
    byte[] pad3 = in.readPad(3);
    List<HierarchyChange> changes = new ArrayList<>(Byte.toUnsignedInt(numChanges));
    for(int i = 0; i < Byte.toUnsignedInt(numChanges); i++) {
      changes.add(HierarchyChange.readHierarchyChange(in));
    }
    javaBuilder.changes(changes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    byte numChanges = (byte) changes.size();
    out.writeCard8(numChanges);
    out.writeCard16((short) getLength());
    out.writePad(3);
    for(HierarchyChange t : changes) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7 + XObject.sizeOf(changes);
  }

  public static class XIChangeHierarchyBuilder {
    public int getSize() {
      return 7 + XObject.sizeOf(changes);
    }
  }
}
