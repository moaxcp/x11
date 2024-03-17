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
public class SetMonitor implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 43;

  private int window;

  @NonNull
  private MonitorInfo monitorinfo;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetMonitor readSetMonitor(X11Input in) throws IOException {
    SetMonitor.SetMonitorBuilder javaBuilder = SetMonitor.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    MonitorInfo monitorinfo = MonitorInfo.readMonitorInfo(in);
    javaBuilder.window(window);
    javaBuilder.monitorinfo(monitorinfo);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    monitorinfo.write(out);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + monitorinfo.getSize();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetMonitorBuilder {
    public int getSize() {
      return 8 + monitorinfo.getSize();
    }
  }
}
