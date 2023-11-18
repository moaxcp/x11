package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RenderLarge implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 2;

  private int contextTag;

  private short requestNum;

  private short requestTotal;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RenderLarge readRenderLarge(X11Input in) throws IOException {
    RenderLarge.RenderLargeBuilder javaBuilder = RenderLarge.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    short requestNum = in.readCard16();
    short requestTotal = in.readCard16();
    int dataLen = in.readCard32();
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(dataLen)));
    javaBuilder.contextTag(contextTag);
    javaBuilder.requestNum(requestNum);
    javaBuilder.requestTotal(requestTotal);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard16(requestNum);
    out.writeCard16(requestTotal);
    int dataLen = data.size();
    out.writeCard32(dataLen);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * data.size();
  }

  public static class RenderLargeBuilder {
    public int getSize() {
      return 16 + 1 * data.size();
    }
  }
}
