package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UnregisterClients implements OneWayRequest {
  public static final String PLUGIN_NAME = "record";

  public static final byte OPCODE = 3;

  private int context;

  @NonNull
  private List<Integer> clientSpecs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UnregisterClients readUnregisterClients(X11Input in) throws IOException {
    UnregisterClients.UnregisterClientsBuilder javaBuilder = UnregisterClients.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    int numClientSpecs = in.readCard32();
    List<Integer> clientSpecs = in.readCard32((int) (Integer.toUnsignedLong(numClientSpecs)));
    javaBuilder.context(context);
    javaBuilder.clientSpecs(clientSpecs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    int numClientSpecs = clientSpecs.size();
    out.writeCard32(numClientSpecs);
    out.writeCard32(clientSpecs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * clientSpecs.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class UnregisterClientsBuilder {
    public int getSize() {
      return 12 + 4 * clientSpecs.size();
    }
  }
}
