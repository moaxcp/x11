package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReadPixels implements TwoWayRequest<ReadPixelsReply>, GlxObject {
  public static final byte OPCODE = 111;

  private int contextTag;

  private int x;

  private int y;

  private int width;

  private int height;

  private int format;

  private int type;

  private boolean swapBytes;

  private boolean lsbFirst;

  public XReplyFunction<ReadPixelsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ReadPixelsReply.readReadPixelsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ReadPixels readReadPixels(X11Input in) throws IOException {
    ReadPixels.ReadPixelsBuilder javaBuilder = ReadPixels.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int x = in.readInt32();
    int y = in.readInt32();
    int width = in.readInt32();
    int height = in.readInt32();
    int format = in.readCard32();
    int type = in.readCard32();
    boolean swapBytes = in.readBool();
    boolean lsbFirst = in.readBool();
    javaBuilder.contextTag(contextTag);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.format(format);
    javaBuilder.type(type);
    javaBuilder.swapBytes(swapBytes);
    javaBuilder.lsbFirst(lsbFirst);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(x);
    out.writeInt32(y);
    out.writeInt32(width);
    out.writeInt32(height);
    out.writeCard32(format);
    out.writeCard32(type);
    out.writeBool(swapBytes);
    out.writeBool(lsbFirst);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 34;
  }

  public static class ReadPixelsBuilder {
    public int getSize() {
      return 34;
    }
  }
}
