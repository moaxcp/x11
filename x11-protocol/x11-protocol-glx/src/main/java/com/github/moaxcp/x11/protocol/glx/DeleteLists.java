package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeleteLists implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 103;

  private int contextTag;

  private int list;

  private int range;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteLists readDeleteLists(X11Input in) throws IOException {
    DeleteLists.DeleteListsBuilder javaBuilder = DeleteLists.builder();
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(list);
    out.writeInt32(range);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeleteListsBuilder {
    public int getSize() {
      return 16;
    }
  }
}
