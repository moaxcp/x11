package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 27;

  private int cid;

  private int source;

  private short x;

  private short y;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateCursor readCreateCursor(X11Input in) throws IOException {
    CreateCursor.CreateCursorBuilder javaBuilder = CreateCursor.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int cid = in.readCard32();
    int source = in.readCard32();
    short x = in.readCard16();
    short y = in.readCard16();
    javaBuilder.cid(cid);
    javaBuilder.source(source);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    out.writeCard32(source);
    out.writeCard16(x);
    out.writeCard16(y);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateCursorBuilder {
    public int getSize() {
      return 16;
    }
  }
}
