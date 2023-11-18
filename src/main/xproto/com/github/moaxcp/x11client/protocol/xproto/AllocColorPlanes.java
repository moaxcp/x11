package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AllocColorPlanes implements TwoWayRequest<AllocColorPlanesReply>, XprotoObject {
  public static final byte OPCODE = 87;

  private boolean contiguous;

  private int cmap;

  private short colors;

  private short reds;

  private short greens;

  private short blues;

  public XReplyFunction<AllocColorPlanesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> AllocColorPlanesReply.readAllocColorPlanesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllocColorPlanes readAllocColorPlanes(X11Input in) throws IOException {
    AllocColorPlanes.AllocColorPlanesBuilder javaBuilder = AllocColorPlanes.builder();
    boolean contiguous = in.readBool();
    short length = in.readCard16();
    int cmap = in.readCard32();
    short colors = in.readCard16();
    short reds = in.readCard16();
    short greens = in.readCard16();
    short blues = in.readCard16();
    javaBuilder.contiguous(contiguous);
    javaBuilder.cmap(cmap);
    javaBuilder.colors(colors);
    javaBuilder.reds(reds);
    javaBuilder.greens(greens);
    javaBuilder.blues(blues);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(contiguous);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard16(colors);
    out.writeCard16(reds);
    out.writeCard16(greens);
    out.writeCard16(blues);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class AllocColorPlanesBuilder {
    public int getSize() {
      return 16;
    }
  }
}
