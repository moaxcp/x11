package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateLease implements TwoWayRequest<CreateLeaseReply>, RandrObject {
  public static final byte OPCODE = 45;

  private int window;

  private int lid;

  @NonNull
  private List<Integer> crtcs;

  @NonNull
  private List<Integer> outputs;

  public XReplyFunction<CreateLeaseReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateLeaseReply.readCreateLeaseReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateLease readCreateLease(X11Input in) throws IOException {
    CreateLease.CreateLeaseBuilder javaBuilder = CreateLease.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int lid = in.readCard32();
    short numCrtcs = in.readCard16();
    short numOutputs = in.readCard16();
    List<Integer> crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    List<Integer> outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    javaBuilder.window(window);
    javaBuilder.lid(lid);
    javaBuilder.crtcs(crtcs);
    javaBuilder.outputs(outputs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class CreateLeaseBuilder {
    public int getSize() {
      return 16 + 4 * crtcs.size() + 4 * outputs.size();
    }
  }
}
