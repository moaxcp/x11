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
public class SetDeviceContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "xselinux";

  public static final byte OPCODE = 3;

  private int device;

  @NonNull
  private ByteList context;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceContext readSetDeviceContext(X11Input in) throws IOException {
    SetDeviceContext.SetDeviceContextBuilder javaBuilder = SetDeviceContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int device = in.readCard32();
    int contextLen = in.readCard32();
    ByteList context = in.readChar((int) (Integer.toUnsignedLong(contextLen)));
    javaBuilder.device(device);
    javaBuilder.context(context.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(device);
    int contextLen = context.size();
    out.writeCard32(contextLen);
    out.writeChar(context);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * context.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetDeviceContextBuilder {
    public int getSize() {
      return 12 + 1 * context.size();
    }
  }
}
