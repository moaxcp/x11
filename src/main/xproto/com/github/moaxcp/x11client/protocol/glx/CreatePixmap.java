package com.github.moaxcp.x11client.protocol.glx;

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
public class CreatePixmap implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 22;

  private int screen;

  private int fbconfig;

  private int pixmap;

  private int glxPixmap;

  @NonNull
  private List<Integer> attribs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreatePixmap readCreatePixmap(X11Input in) throws IOException {
    CreatePixmap.CreatePixmapBuilder javaBuilder = CreatePixmap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int screen = in.readCard32();
    int fbconfig = in.readCard32();
    int pixmap = in.readCard32();
    int glxPixmap = in.readCard32();
    int numAttribs = in.readCard32();
    List<Integer> attribs = in.readCard32((int) (Integer.toUnsignedLong(numAttribs) * 2));
    javaBuilder.screen(screen);
    javaBuilder.fbconfig(fbconfig);
    javaBuilder.pixmap(pixmap);
    javaBuilder.glxPixmap(glxPixmap);
    javaBuilder.attribs(attribs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(fbconfig);
    out.writeCard32(pixmap);
    out.writeCard32(glxPixmap);
    int numAttribs = attribs.size();
    out.writeCard32(numAttribs);
    out.writeCard32(attribs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 4 * attribs.size();
  }

  public static class CreatePixmapBuilder {
    public int getSize() {
      return 24 + 4 * attribs.size();
    }
  }
}
