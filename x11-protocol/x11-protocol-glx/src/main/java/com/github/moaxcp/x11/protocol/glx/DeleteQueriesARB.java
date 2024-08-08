package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class DeleteQueriesARB implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = (byte) 161;

  private int contextTag;

  @NonNull
  private IntList ids;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteQueriesARB readDeleteQueriesARB(X11Input in) throws IOException {
    DeleteQueriesARB.DeleteQueriesARBBuilder javaBuilder = DeleteQueriesARB.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int n = in.readInt32();
    IntList ids = in.readCard32(n);
    javaBuilder.contextTag(contextTag);
    javaBuilder.ids(ids.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    int n = ids.size();
    out.writeInt32(n);
    out.writeCard32(ids);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * ids.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeleteQueriesARBBuilder {
    public int getSize() {
      return 12 + 4 * ids.size();
    }
  }
}
