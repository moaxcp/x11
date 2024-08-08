package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class CreateLease implements TwoWayRequest<CreateLeaseReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 45;

  private int window;

  private int lid;

  @NonNull
  private IntList crtcs;

  @NonNull
  private IntList outputs;

  public XReplyFunction<CreateLeaseReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateLeaseReply.readCreateLeaseReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateLease readCreateLease(X11Input in) throws IOException {
    CreateLease.CreateLeaseBuilder javaBuilder = CreateLease.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int lid = in.readCard32();
    short numCrtcs = in.readCard16();
    short numOutputs = in.readCard16();
    IntList crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    IntList outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    javaBuilder.window(window);
    javaBuilder.lid(lid);
    javaBuilder.crtcs(crtcs.toImmutable());
    javaBuilder.outputs(outputs.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(lid);
    short numCrtcs = (short) crtcs.size();
    out.writeCard16(numCrtcs);
    short numOutputs = (short) outputs.size();
    out.writeCard16(numOutputs);
    out.writeCard32(crtcs);
    out.writeCard32(outputs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 4 * crtcs.size() + 4 * outputs.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateLeaseBuilder {
    public int getSize() {
      return 16 + 4 * crtcs.size() + 4 * outputs.size();
    }
  }
}
