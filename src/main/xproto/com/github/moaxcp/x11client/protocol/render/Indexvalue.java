package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Indexvalue implements XStruct, RenderObject {
  private int pixel;

  private short red;

  private short green;

  private short blue;

  private short alpha;

  public static Indexvalue readIndexvalue(X11Input in) throws IOException {
    Indexvalue.IndexvalueBuilder javaBuilder = Indexvalue.builder();
    int pixel = in.readCard32();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    short alpha = in.readCard16();
    javaBuilder.pixel(pixel);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    javaBuilder.alpha(alpha);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(pixel);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writeCard16(alpha);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class IndexvalueBuilder {
    public int getSize() {
      return 12;
    }
  }
}
