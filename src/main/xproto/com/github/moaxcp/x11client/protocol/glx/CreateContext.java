package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateContext implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 3;

  private int context;

  private int visual;

  private int screen;

  private int shareList;

  private boolean direct;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateContext readCreateContext(X11Input in) throws IOException {
    CreateContext.CreateContextBuilder javaBuilder = CreateContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int context = in.readCard32();
    int visual = in.readCard32();
    int screen = in.readCard32();
    int shareList = in.readCard32();
    boolean direct = in.readBool();
    byte[] pad8 = in.readPad(3);
    javaBuilder.context(context);
    javaBuilder.visual(visual);
    javaBuilder.screen(screen);
    javaBuilder.shareList(shareList);
    javaBuilder.direct(direct);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    out.writeCard32(visual);
    out.writeCard32(screen);
    out.writeCard32(shareList);
    out.writeBool(direct);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class CreateContextBuilder {
    public int getSize() {
      return 24;
    }
  }
}
