package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AllocColorPlanesReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  private int redMask;

  private int greenMask;

  private int blueMask;

  @NonNull
  private List<Integer> pixels;

  public static AllocColorPlanesReply readAllocColorPlanesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    AllocColorPlanesReply.AllocColorPlanesReplyBuilder javaBuilder = AllocColorPlanesReply.builder();
    int length = in.readCard32();
    short pixelsLen = in.readCard16();
    byte[] pad5 = in.readPad(2);
    int redMask = in.readCard32();
    int greenMask = in.readCard32();
    int blueMask = in.readCard32();
    byte[] pad9 = in.readPad(8);
    List<Integer> pixels = in.readCard32(Short.toUnsignedInt(pixelsLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.redMask(redMask);
    javaBuilder.greenMask(greenMask);
    javaBuilder.blueMask(blueMask);
    javaBuilder.pixels(pixels);
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
    short pixelsLen = (short) pixels.size();
    out.writeCard16(pixelsLen);
    out.writePad(2);
    out.writeCard32(redMask);
    out.writeCard32(greenMask);
    out.writeCard32(blueMask);
    out.writePad(8);
    out.writeCard32(pixels);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * pixels.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AllocColorPlanesReplyBuilder {
    public int getSize() {
      return 32 + 4 * pixels.size();
    }
  }
}
