package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetOutputInfo implements TwoWayRequest<GetOutputInfoReply>, RandrObject {
  public static final byte OPCODE = 9;

  private int output;

  private int configTimestamp;

  public XReplyFunction<GetOutputInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetOutputInfoReply.readGetOutputInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetOutputInfo readGetOutputInfo(X11Input in) throws IOException {
    GetOutputInfo.GetOutputInfoBuilder javaBuilder = GetOutputInfo.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int output = in.readCard32();
    int configTimestamp = in.readCard32();
    javaBuilder.output(output);
    javaBuilder.configTimestamp(configTimestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(configTimestamp);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetOutputInfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
