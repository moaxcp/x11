package com.github.moaxcp.x11.protocol.xprint;

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
public class PrintGetOneAttributes implements TwoWayRequest<PrintGetOneAttributesReply> {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 19;

  private int context;

  private byte pool;

  @NonNull
  private ByteList name;

  public XReplyFunction<PrintGetOneAttributesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintGetOneAttributesReply.readPrintGetOneAttributesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintGetOneAttributes readPrintGetOneAttributes(X11Input in) throws IOException {
    PrintGetOneAttributes.PrintGetOneAttributesBuilder javaBuilder = PrintGetOneAttributes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    int nameLen = in.readCard32();
    byte pool = in.readCard8();
    byte[] pad6 = in.readPad(3);
    ByteList name = in.readChar((int) (Integer.toUnsignedLong(nameLen)));
    javaBuilder.context(context);
    javaBuilder.pool(pool);
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetOneAttributesBuilder {
    public int getSize() {
      return 16 + 1 * name.size();
    }
  }
}
