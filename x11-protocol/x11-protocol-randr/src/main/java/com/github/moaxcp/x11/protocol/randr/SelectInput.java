package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SelectInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 4;

  private int window;

  private short enable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectInput readSelectInput(X11Input in) throws IOException {
    SelectInput.SelectInputBuilder javaBuilder = SelectInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    short enable = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.enable(enable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard16(enable);
    out.writePad(2);
  }

  public boolean isEnableEnabled(@NonNull NotifyMask... maskEnums) {
    for(NotifyMask m : maskEnums) {
      if(!m.isEnabled(enable)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SelectInputBuilder {
    public boolean isEnableEnabled(@NonNull NotifyMask... maskEnums) {
      for(NotifyMask m : maskEnums) {
        if(!m.isEnabled(enable)) {
          return false;
        }
      }
      return true;
    }

    public SelectInput.SelectInputBuilder enableEnable(NotifyMask... maskEnums) {
      for(NotifyMask m : maskEnums) {
        enable((short) m.enableFor(enable));
      }
      return this;
    }

    public SelectInput.SelectInputBuilder enableDisable(NotifyMask... maskEnums) {
      for(NotifyMask m : maskEnums) {
        enable((short) m.disableFor(enable));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
