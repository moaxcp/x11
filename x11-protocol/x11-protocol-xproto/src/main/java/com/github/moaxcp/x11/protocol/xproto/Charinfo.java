package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Charinfo implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private short leftSideBearing;

  private short rightSideBearing;

  private short characterWidth;

  private short ascent;

  private short descent;

  private short attributes;

  public static Charinfo readCharinfo(X11Input in) throws IOException {
    Charinfo.CharinfoBuilder javaBuilder = Charinfo.builder();
    short leftSideBearing = in.readInt16();
    short rightSideBearing = in.readInt16();
    short characterWidth = in.readInt16();
    short ascent = in.readInt16();
    short descent = in.readInt16();
    short attributes = in.readCard16();
    javaBuilder.leftSideBearing(leftSideBearing);
    javaBuilder.rightSideBearing(rightSideBearing);
    javaBuilder.characterWidth(characterWidth);
    javaBuilder.ascent(ascent);
    javaBuilder.descent(descent);
    javaBuilder.attributes(attributes);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(leftSideBearing);
    out.writeInt16(rightSideBearing);
    out.writeInt16(characterWidth);
    out.writeInt16(ascent);
    out.writeInt16(descent);
    out.writeCard16(attributes);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CharinfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
