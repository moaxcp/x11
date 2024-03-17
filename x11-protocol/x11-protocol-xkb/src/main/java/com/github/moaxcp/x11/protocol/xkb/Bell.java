package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Bell implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 3;

  private short deviceSpec;

  private short bellClass;

  private short bellID;

  private byte percent;

  private boolean forceSound;

  private boolean eventOnly;

  private short pitch;

  private short duration;

  private int name;

  private int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Bell readBell(X11Input in) throws IOException {
    Bell.BellBuilder javaBuilder = Bell.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short bellClass = in.readCard16();
    short bellID = in.readCard16();
    byte percent = in.readInt8();
    boolean forceSound = in.readBool();
    boolean eventOnly = in.readBool();
    byte[] pad9 = in.readPad(1);
    short pitch = in.readInt16();
    short duration = in.readInt16();
    byte[] pad12 = in.readPad(2);
    int name = in.readCard32();
    int window = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.bellClass(bellClass);
    javaBuilder.bellID(bellID);
    javaBuilder.percent(percent);
    javaBuilder.forceSound(forceSound);
    javaBuilder.eventOnly(eventOnly);
    javaBuilder.pitch(pitch);
    javaBuilder.duration(duration);
    javaBuilder.name(name);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(bellClass);
    out.writeCard16(bellID);
    out.writeInt8(percent);
    out.writeBool(forceSound);
    out.writeBool(eventOnly);
    out.writePad(1);
    out.writeInt16(pitch);
    out.writeInt16(duration);
    out.writePad(2);
    out.writeCard32(name);
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class BellBuilder {
    public int getSize() {
      return 28;
    }
  }
}
