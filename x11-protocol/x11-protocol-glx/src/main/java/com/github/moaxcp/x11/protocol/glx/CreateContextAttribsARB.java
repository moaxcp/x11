package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateContextAttribsARB implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

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
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateContextAttribsARBBuilder {
    public int getSize() {
      return 28 + 4 * attribs.size();
    }
  }
}
