package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Char2b implements XStruct, XprotoObject {
  private byte byte1;

  private byte byte2;

  public static Char2b readChar2b(X11Input in) throws IOException {
    Char2b.Char2bBuilder javaBuilder = Char2b.builder();
    byte byte1 = in.readCard8();
    byte byte2 = in.readCard8();
    javaBuilder.byte1(byte1);
    javaBuilder.byte2(byte2);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(byte1);
    out.writeCard8(byte2);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public static class Char2bBuilder {
    public int getSize() {
      return 2;
    }
  }
}
