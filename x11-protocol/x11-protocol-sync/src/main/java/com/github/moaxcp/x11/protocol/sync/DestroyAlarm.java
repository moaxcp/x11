package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyAlarm implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 11;

  private int alarm;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyAlarm readDestroyAlarm(X11Input in) throws IOException {
    DestroyAlarm.DestroyAlarmBuilder javaBuilder = DestroyAlarm.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int alarm = in.readCard32();
    javaBuilder.alarm(alarm);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(alarm);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DestroyAlarmBuilder {
    public int getSize() {
      return 8;
    }
  }
}
