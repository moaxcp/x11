package com.github.moaxcp.x11client.protocol.xfixes;

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
public class GetCursorImageReply implements XReply, XfixesObject {
  private short sequenceNumber;

  private short x;

  private short y;

  private short width;

  private short height;

  private short xhot;

  private short yhot;

  private int cursorSerial;

  @NonNull
  private List<Integer> cursorImage;

  public static GetCursorImageReply readGetCursorImageReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetCursorImageReply.GetCursorImageReplyBuilder javaBuilder = GetCursorImageReply.builder();
    int length = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short xhot = in.readCard16();
    short yhot = in.readCard16();
    int cursorSerial = in.readCard32();
    byte[] pad11 = in.readPad(8);
    List<Integer> cursorImage = in.readCard32(Short.toUnsignedInt(width) * Short.toUnsignedInt(height));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.xhot(xhot);
    javaBuilder.yhot(yhot);
    javaBuilder.cursorSerial(cursorSerial);
    javaBuilder.cursorImage(cursorImage);
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
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(xhot);
    out.writeCard16(yhot);
    out.writeCard32(cursorSerial);
    out.writePad(8);
    out.writeCard32(cursorImage);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * cursorImage.size();
  }

  public static class GetCursorImageReplyBuilder {
    public int getSize() {
      return 32 + 4 * cursorImage.size();
    }
  }
}
