package com.github.moaxcp.x11.protocol.xvmc;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListSurfaceTypes implements TwoWayRequest<ListSurfaceTypesReply> {
  public static final String PLUGIN_NAME = "xvmc";

  public static final byte OPCODE = 1;

  private int portId;

  public XReplyFunction<ListSurfaceTypesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListSurfaceTypesReply.readListSurfaceTypesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListSurfaceTypes readListSurfaceTypes(X11Input in) throws IOException {
    ListSurfaceTypes.ListSurfaceTypesBuilder javaBuilder = ListSurfaceTypes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int portId = in.readCard32();
    javaBuilder.portId(portId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(portId);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListSurfaceTypesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
