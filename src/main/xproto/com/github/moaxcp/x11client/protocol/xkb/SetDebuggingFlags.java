package com.github.moaxcp.x11client.protocol.xkb;

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
public class SetDebuggingFlags implements TwoWayRequest<SetDebuggingFlagsReply>, XkbObject {
  public static final byte OPCODE = 101;

  private int affectFlags;

  private int flags;

  private int affectCtrls;

  private int ctrls;

  @NonNull
  private List<Byte> message;

  public XReplyFunction<SetDebuggingFlagsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetDebuggingFlagsReply.readSetDebuggingFlagsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDebuggingFlags readSetDebuggingFlags(X11Input in) throws IOException {
    SetDebuggingFlags.SetDebuggingFlagsBuilder javaBuilder = SetDebuggingFlags.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short msgLength = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int affectFlags = in.readCard32();
    int flags = in.readCard32();
    int affectCtrls = in.readCard32();
    int ctrls = in.readCard32();
    List<Byte> message = in.readChar(Short.toUnsignedInt(msgLength));
    javaBuilder.affectFlags(affectFlags);
    javaBuilder.flags(flags);
    javaBuilder.affectCtrls(affectCtrls);
    javaBuilder.ctrls(ctrls);
    javaBuilder.message(message);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    short msgLength = (short) message.size();
    out.writeCard16(msgLength);
    out.writePad(2);
    out.writeCard32(affectFlags);
    out.writeCard32(flags);
    out.writeCard32(affectCtrls);
    out.writeCard32(ctrls);
    out.writeChar(message);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * message.size();
  }

  public static class SetDebuggingFlagsBuilder {
    public int getSize() {
      return 24 + 1 * message.size();
    }
  }
}
