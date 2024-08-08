package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SetDebuggingFlags implements TwoWayRequest<SetDebuggingFlagsReply> {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 101;

  private int affectFlags;

  private int flags;

  private int affectCtrls;

  private int ctrls;

  @NonNull
  private ByteList message;

  public XReplyFunction<SetDebuggingFlagsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetDebuggingFlagsReply.readSetDebuggingFlagsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDebuggingFlags readSetDebuggingFlags(X11Input in) throws IOException {
    SetDebuggingFlags.SetDebuggingFlagsBuilder javaBuilder = SetDebuggingFlags.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short msgLength = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int affectFlags = in.readCard32();
    int flags = in.readCard32();
    int affectCtrls = in.readCard32();
    int ctrls = in.readCard32();
    ByteList message = in.readChar(Short.toUnsignedInt(msgLength));
    javaBuilder.affectFlags(affectFlags);
    javaBuilder.flags(flags);
    javaBuilder.affectCtrls(affectCtrls);
    javaBuilder.ctrls(ctrls);
    javaBuilder.message(message.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetDebuggingFlagsBuilder {
    public int getSize() {
      return 24 + 1 * message.size();
    }
  }
}
