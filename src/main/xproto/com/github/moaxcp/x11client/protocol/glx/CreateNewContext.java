package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateNewContext implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 24;

  private int context;

  private int fbconfig;

  private int screen;

  private int renderType;

  private int shareList;

  private boolean direct;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateNewContext readCreateNewContext(X11Input in) throws IOException {
    CreateNewContext.CreateNewContextBuilder javaBuilder = CreateNewContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int context = in.readCard32();
    int fbconfig = in.readCard32();
    int screen = in.readCard32();
    int renderType = in.readCard32();
    int shareList = in.readCard32();
    boolean direct = in.readBool();
    byte[] pad9 = in.readPad(3);
    javaBuilder.context(context);
    javaBuilder.fbconfig(fbconfig);
    javaBuilder.screen(screen);
    javaBuilder.renderType(renderType);
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
    out.writeCard32(fbconfig);
    out.writeCard32(screen);
    out.writeCard32(renderType);
    out.writeCard32(shareList);
    out.writeBool(direct);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class CreateNewContextBuilder {
    public int getSize() {
      return 28;
    }
  }
}
