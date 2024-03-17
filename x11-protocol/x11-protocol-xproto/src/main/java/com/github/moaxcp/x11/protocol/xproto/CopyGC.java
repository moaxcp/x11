package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CopyGC implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 57;

  private int srcGc;

  private int dstGc;

  private int valueMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CopyGC readCopyGC(X11Input in) throws IOException {
    CopyGC.CopyGCBuilder javaBuilder = CopyGC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int srcGc = in.readCard32();
    int dstGc = in.readCard32();
    int valueMask = in.readCard32();
    javaBuilder.srcGc(srcGc);
    javaBuilder.dstGc(dstGc);
    javaBuilder.valueMask(valueMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(srcGc);
    out.writeCard32(dstGc);
    out.writeCard32(valueMask);
  }

  public boolean isValueMaskEnabled(@NonNull Gc... maskEnums) {
    for(Gc m : maskEnums) {
      if(!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CopyGCBuilder {
    private CopyGC.CopyGCBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(@NonNull Gc... maskEnums) {
      for(Gc m : maskEnums) {
        if(!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private CopyGC.CopyGCBuilder valueMaskEnable(Gc... maskEnums) {
      for(Gc m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private CopyGC.CopyGCBuilder valueMaskDisable(Gc... maskEnums) {
      for(Gc m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
