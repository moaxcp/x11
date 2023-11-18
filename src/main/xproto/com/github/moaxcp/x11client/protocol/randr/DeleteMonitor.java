package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeleteMonitor implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 44;

  private int window;

  private int name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteMonitor readDeleteMonitor(X11Input in) throws IOException {
    DeleteMonitor.DeleteMonitorBuilder javaBuilder = DeleteMonitor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int name = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.name(name);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(name);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class DeleteMonitorBuilder {
    public int getSize() {
      return 12;
    }
  }
}
