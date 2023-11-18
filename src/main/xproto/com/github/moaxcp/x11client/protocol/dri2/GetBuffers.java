package com.github.moaxcp.x11client.protocol.dri2;

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
public class GetBuffers implements TwoWayRequest<GetBuffersReply>, Dri2Object {
  public static final byte OPCODE = 5;

  private int drawable;

  private int count;

  @NonNull
  private List<Integer> attachments;

  public XReplyFunction<GetBuffersReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetBuffersReply.readGetBuffersReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetBuffers readGetBuffers(X11Input in) throws IOException {
    GetBuffers.GetBuffersBuilder javaBuilder = GetBuffers.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int count = in.readCard32();
    javaStart += 4;
    List<Integer> attachments = in.readCard32(javaStart - length);
    javaBuilder.drawable(drawable);
    javaBuilder.count(count);
    javaBuilder.attachments(attachments);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(count);
    out.writeCard32(attachments);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * attachments.size();
  }

  public static class GetBuffersBuilder {
    public int getSize() {
      return 12 + 4 * attachments.size();
    }
  }
}
