package com.github.moaxcp.x11.protocol.xselinux;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SetSelectionUseContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "xselinux";

  public static final byte OPCODE = 17;

  @NonNull
  private ByteList context;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetSelectionUseContext readSetSelectionUseContext(X11Input in) throws IOException {
    SetSelectionUseContext.SetSelectionUseContextBuilder javaBuilder = SetSelectionUseContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextLen = in.readCard32();
    ByteList context = in.readChar((int) (Integer.toUnsignedLong(contextLen)));
    javaBuilder.context(context.toImmutable());
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

  public static class SetSelectionUseContextBuilder {
    public int getSize() {
      return 8 + 1 * context.size();
    }
  }
}
