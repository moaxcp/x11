package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class AllocColorCellsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private IntList pixels;

  @NonNull
  private IntList masks;

  public static AllocColorCellsReply readAllocColorCellsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    AllocColorCellsReply.AllocColorCellsReplyBuilder javaBuilder = AllocColorCellsReply.builder();
    int length = in.readCard32();
    short pixelsLen = in.readCard16();
    short masksLen = in.readCard16();
    byte[] pad6 = in.readPad(20);
    IntList pixels = in.readCard32(Short.toUnsignedInt(pixelsLen));
    IntList masks = in.readCard32(Short.toUnsignedInt(masksLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pixels(pixels.toImmutable());
    javaBuilder.masks(masks.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
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
    short masksLen = (short) masks.size();
    out.writeCard16(masksLen);
    out.writePad(20);
    out.writeCard32(pixels);
    out.writeCard32(masks);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * pixels.size() + 4 * masks.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AllocColorCellsReplyBuilder {
    public int getSize() {
      return 32 + 4 * pixels.size() + 4 * masks.size();
    }
  }
}
