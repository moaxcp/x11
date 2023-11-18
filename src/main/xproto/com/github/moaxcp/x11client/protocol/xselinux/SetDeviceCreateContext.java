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
public class SetDeviceCreateContext implements OneWayRequest, XselinuxObject {
  public static final byte OPCODE = 1;

  @NonNull
  private List<Byte> context;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceCreateContext readSetDeviceCreateContext(X11Input in) throws IOException {
    SetDeviceCreateContext.SetDeviceCreateContextBuilder javaBuilder = SetDeviceCreateContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextLen = in.readCard32();
    List<Byte> context = in.readChar((int) (Integer.toUnsignedLong(contextLen)));
    javaBuilder.context(context);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class SetDeviceCreateContextBuilder {
    public int getSize() {
      return 8 + 1 * context.size();
    }
  }
}
