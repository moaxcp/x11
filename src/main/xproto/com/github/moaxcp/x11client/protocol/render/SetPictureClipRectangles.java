package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetPictureClipRectangles implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 6;

  private int picture;

  private short clipXOrigin;

  private short clipYOrigin;

  @NonNull
  private List<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPictureClipRectangles readSetPictureClipRectangles(X11Input in) throws
      IOException {
    SetPictureClipRectangles.SetPictureClipRectanglesBuilder javaBuilder = SetPictureClipRectangles.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int picture = in.readCard32();
    javaStart += 4;
    short clipXOrigin = in.readInt16();
    javaStart += 2;
    short clipYOrigin = in.readInt16();
    javaStart += 2;
    List<Rectangle> rectangles = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.picture(picture);
    javaBuilder.clipXOrigin(clipXOrigin);
    javaBuilder.clipYOrigin(clipYOrigin);
    javaBuilder.rectangles(rectangles);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    out.writeInt16(clipXOrigin);
    out.writeInt16(clipYOrigin);
    for(Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(rectangles);
  }

  public static class SetPictureClipRectanglesBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(rectangles);
    }
  }
}
