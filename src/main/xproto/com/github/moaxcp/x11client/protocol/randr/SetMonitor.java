package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetMonitor implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 43;

  private int window;

  @NonNull
  private MonitorInfo monitorinfo;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetMonitor readSetMonitor(X11Input in) throws IOException {
    SetMonitor.SetMonitorBuilder javaBuilder = SetMonitor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    MonitorInfo monitorinfo = MonitorInfo.readMonitorInfo(in);
    javaBuilder.window(window);
    javaBuilder.monitorinfo(monitorinfo);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    monitorinfo.write(out);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + monitorinfo.getSize();
  }

  public static class SetMonitorBuilder {
    public int getSize() {
      return 8 + monitorinfo.getSize();
    }
  }
}
