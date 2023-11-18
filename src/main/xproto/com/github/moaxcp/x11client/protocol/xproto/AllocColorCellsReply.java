package com.github.moaxcp.x11client.protocol.xproto;

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
public class AllocColorCellsReply implements XReply, XprotoObject {
  private short sequenceNumber;

  @NonNull
  private List<Integer> pixels;

  @NonNull
  private List<Integer> masks;

  public static AllocColorCellsReply readAllocColorCellsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    AllocColorCellsReply.AllocColorCellsReplyBuilder javaBuilder = AllocColorCellsReply.builder();
    int length = in.readCard32();
    short pixelsLen = in.readCard16();
    short masksLen = in.readCard16();
    byte[] pad6 = in.readPad(20);
    List<Integer> pixels = in.readCard32(Short.toUnsignedInt(pixelsLen));
    List<Integer> masks = in.readCard32(Short.toUnsignedInt(masksLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pixels(pixels);
    javaBuilder.masks(masks);
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

  public static class AllocColorCellsReplyBuilder {
    public int getSize() {
      return 32 + 4 * pixels.size() + 4 * masks.size();
    }
  }
}
