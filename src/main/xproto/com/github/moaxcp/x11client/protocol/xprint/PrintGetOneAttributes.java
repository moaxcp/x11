package com.github.moaxcp.x11client.protocol.xprint;

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
public class PrintGetOneAttributes implements TwoWayRequest<PrintGetOneAttributesReply>, XprintObject {
  public static final byte OPCODE = 19;

  private int context;

  private byte pool;

  @NonNull
  private List<Byte> name;

  public XReplyFunction<PrintGetOneAttributesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintGetOneAttributesReply.readPrintGetOneAttributesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintGetOneAttributes readPrintGetOneAttributes(X11Input in) throws IOException {
    PrintGetOneAttributes.PrintGetOneAttributesBuilder javaBuilder = PrintGetOneAttributes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int context = in.readCard32();
    int nameLen = in.readCard32();
    byte pool = in.readCard8();
    byte[] pad6 = in.readPad(3);
    List<Byte> name = in.readChar((int) (Integer.toUnsignedLong(nameLen)));
    javaBuilder.context(context);
    javaBuilder.pool(pool);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    int nameLen = name.size();
    out.writeCard32(nameLen);
    out.writeCard8(pool);
    out.writePad(3);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * name.size();
  }

  public static class PrintGetOneAttributesBuilder {
    public int getSize() {
      return 16 + 1 * name.size();
    }
  }
}
