package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Segment implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private short x1;

  private short y1;

  private short x2;

  private short y2;

  public static Segment readSegment(X11Input in) throws IOException {
    Segment.SegmentBuilder javaBuilder = Segment.builder();
    short x1 = in.readInt16();
    short y1 = in.readInt16();
    short x2 = in.readInt16();
    short y2 = in.readInt16();
    javaBuilder.x1(x1);
    javaBuilder.y1(y1);
    javaBuilder.x2(x2);
    javaBuilder.y2(y2);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(x1);
    out.writeInt16(y1);
    out.writeInt16(x2);
    out.writeInt16(y2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SegmentBuilder {
    public int getSize() {
      return 8;
    }
  }
}
