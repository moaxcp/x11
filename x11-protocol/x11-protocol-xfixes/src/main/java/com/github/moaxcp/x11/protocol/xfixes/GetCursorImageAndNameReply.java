package com.github.moaxcp.x11.protocol.xfixes;

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
public class GetCursorImageAndNameReply implements XReply {
  public static final String PLUGIN_NAME = "xfixes";

  private short sequenceNumber;

  private short x;

  private short y;

  private short width;

  private short height;

  private short xhot;

  private short yhot;

  private int cursorSerial;

  private int cursorAtom;

  @NonNull
  private List<Integer> cursorImage;

  @NonNull
  private List<Byte> name;

  public static GetCursorImageAndNameReply readGetCursorImageAndNameReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetCursorImageAndNameReply.GetCursorImageAndNameReplyBuilder javaBuilder = GetCursorImageAndNameReply.builder();
    int length = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short xhot = in.readCard16();
    short yhot = in.readCard16();
    int cursorSerial = in.readCard32();
    int cursorAtom = in.readCard32();
    short nbytes = in.readCard16();
    byte[] pad13 = in.readPad(2);
    List<Integer> cursorImage = in.readCard32(Short.toUnsignedInt(width) * Short.toUnsignedInt(height));
    List<Byte> name = in.readChar(Short.toUnsignedInt(nbytes));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.xhot(xhot);
    javaBuilder.yhot(yhot);
    javaBuilder.cursorSerial(cursorSerial);
    javaBuilder.cursorAtom(cursorAtom);
    javaBuilder.cursorImage(cursorImage);
    javaBuilder.name(name);
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
    out.writeCard32(cursorAtom);
    short nbytes = (short) name.size();
    out.writeCard16(nbytes);
    out.writePad(2);
    out.writeCard32(cursorImage);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * cursorImage.size() + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetCursorImageAndNameReplyBuilder {
    public int getSize() {
      return 32 + 4 * cursorImage.size() + 1 * name.size();
    }
  }
}
