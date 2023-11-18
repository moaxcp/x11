package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Initialize implements TwoWayRequest<InitializeReply>, SyncObject {
  public static final byte OPCODE = 0;

  private byte desiredMajorVersion;

  private byte desiredMinorVersion;

  public XReplyFunction<InitializeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> InitializeReply.readInitializeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Initialize readInitialize(X11Input in) throws IOException {
    Initialize.InitializeBuilder javaBuilder = Initialize.builder();
    byte desiredMajorVersion = in.readCard8();
    short length = in.readCard16();
    byte desiredMinorVersion = in.readCard8();
    javaBuilder.desiredMajorVersion(desiredMajorVersion);
    javaBuilder.desiredMinorVersion(desiredMinorVersion);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(desiredMajorVersion);
    out.writeCard16((short) getLength());
    out.writeCard8(desiredMinorVersion);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 5;
  }

  public static class InitializeBuilder {
    public int getSize() {
      return 5;
    }
  }
}
