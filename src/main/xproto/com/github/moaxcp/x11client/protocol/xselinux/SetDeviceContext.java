package com.github.moaxcp.x11client.protocol.xselinux;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetDeviceContext implements OneWayRequest, XselinuxObject {
  public static final byte OPCODE = 3;

  private int device;

  @NonNull
  private List<Byte> context;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceContext readSetDeviceContext(X11Input in) throws IOException {
    SetDeviceContext.SetDeviceContextBuilder javaBuilder = SetDeviceContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int device = in.readCard32();
    int contextLen = in.readCard32();
    List<Byte> context = in.readChar((int) (Integer.toUnsignedLong(contextLen)));
    javaBuilder.device(device);
    javaBuilder.context(context);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class SetDeviceContextBuilder {
    public int getSize() {
      return 12 + 1 * context.size();
    }
  }
}
