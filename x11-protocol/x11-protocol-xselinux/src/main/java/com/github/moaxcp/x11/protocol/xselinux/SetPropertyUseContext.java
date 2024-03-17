package com.github.moaxcp.x11.protocol.xselinux;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetPropertyUseContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "xselinux";

  public static final byte OPCODE = 10;

  @NonNull
  private List<Byte> context;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPropertyUseContext readSetPropertyUseContext(X11Input in) throws IOException {
    SetPropertyUseContext.SetPropertyUseContextBuilder javaBuilder = SetPropertyUseContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextLen = in.readCard32();
    List<Byte> context = in.readChar((int) (Integer.toUnsignedLong(contextLen)));
    javaBuilder.context(context);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    int contextLen = context.size();
    out.writeCard32(contextLen);
    out.writeChar(context);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * context.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetPropertyUseContextBuilder {
    public int getSize() {
      return 8 + 1 * context.size();
    }
  }
}
