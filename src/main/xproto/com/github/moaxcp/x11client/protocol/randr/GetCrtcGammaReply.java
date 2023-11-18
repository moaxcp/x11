package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetCrtcGammaReply implements XReply, RandrObject {
  private short sequenceNumber;

  @NonNull
  private List<Short> red;

  @NonNull
  private List<Short> green;

  @NonNull
  private List<Short> blue;

  public static GetCrtcGammaReply readGetCrtcGammaReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetCrtcGammaReply.GetCrtcGammaReplyBuilder javaBuilder = GetCrtcGammaReply.builder();
    int length = in.readCard32();
    short size = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<Short> red = in.readCard16(Short.toUnsignedInt(size));
    List<Short> green = in.readCard16(Short.toUnsignedInt(size));
    List<Short> blue = in.readCard16(Short.toUnsignedInt(size));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short size = (short) blue.size();
    out.writeCard16(size);
    out.writePad(22);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
  }

  public static class GetCrtcGammaReplyBuilder {
    public int getSize() {
      return 32 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
    }
  }
}
