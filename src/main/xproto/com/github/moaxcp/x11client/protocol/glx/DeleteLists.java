package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeleteLists implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 103;

  private int contextTag;

  private int list;

  private int range;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteLists readDeleteLists(X11Input in) throws IOException {
    DeleteLists.DeleteListsBuilder javaBuilder = DeleteLists.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int list = in.readCard32();
    int range = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.list(list);
    javaBuilder.range(range);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(list);
    out.writeInt32(range);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class DeleteListsBuilder {
    public int getSize() {
      return 16;
    }
  }
}
