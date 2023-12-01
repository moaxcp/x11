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
public class AllocColorCells implements TwoWayRequest<AllocColorCellsReply>, XprotoObject {
  public static final byte OPCODE = 86;

  private boolean contiguous;

  private int cmap;

  private short colors;

  private short planes;

  public XReplyFunction<AllocColorCellsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> AllocColorCellsReply.readAllocColorCellsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllocColorCells readAllocColorCells(X11Input in) throws IOException {
    AllocColorCells.AllocColorCellsBuilder javaBuilder = AllocColorCells.builder();
    boolean contiguous = in.readBool();
    short length = in.readCard16();
    int cmap = in.readCard32();
    short colors = in.readCard16();
    short planes = in.readCard16();
    javaBuilder.contiguous(contiguous);
    javaBuilder.cmap(cmap);
    javaBuilder.colors(colors);
    javaBuilder.planes(planes);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(contiguous);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard16(colors);
    out.writeCard16(planes);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class AllocColorCellsBuilder {
    public int getSize() {
      return 12;
    }
  }
}
