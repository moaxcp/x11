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
public class CreateContextAttribsARB implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 34;

  private int context;

  private int fbconfig;

  private int screen;

  private int shareList;

  private boolean direct;

  @NonNull
  private List<Integer> attribs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateContextAttribsARB readCreateContextAttribsARB(X11Input in) throws
      IOException {
    CreateContextAttribsARB.CreateContextAttribsARBBuilder javaBuilder = CreateContextAttribsARB.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int context = in.readCard32();
    int fbconfig = in.readCard32();
    int screen = in.readCard32();
    int shareList = in.readCard32();
    boolean direct = in.readBool();
    byte[] pad8 = in.readPad(3);
    int numAttribs = in.readCard32();
    List<Integer> attribs = in.readCard32((int) (Integer.toUnsignedLong(numAttribs) * 2));
    javaBuilder.context(context);
    javaBuilder.fbconfig(fbconfig);
    javaBuilder.screen(screen);
    javaBuilder.shareList(shareList);
    javaBuilder.direct(direct);
    javaBuilder.attribs(attribs);
    in.readPadAlign(javaBuilder.getSize());
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
    out.writeCard32(shareList);
    out.writeBool(direct);
    out.writePad(3);
    int numAttribs = attribs.size();
    out.writeCard32(numAttribs);
    out.writeCard32(attribs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 28 + 4 * attribs.size();
  }

  public static class CreateContextAttribsARBBuilder {
    public int getSize() {
      return 28 + 4 * attribs.size();
    }
  }
}
