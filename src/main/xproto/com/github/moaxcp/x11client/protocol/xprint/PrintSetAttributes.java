package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PrintSetAttributes implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 18;

  private int context;

  private int stringLen;

  private byte pool;

  private byte rule;

  @NonNull
  private List<Byte> attributes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintSetAttributes readPrintSetAttributes(X11Input in) throws IOException {
    PrintSetAttributes.PrintSetAttributesBuilder javaBuilder = PrintSetAttributes.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int context = in.readCard32();
    javaStart += 4;
    int stringLen = in.readCard32();
    javaStart += 4;
    byte pool = in.readCard8();
    javaStart += 1;
    byte rule = in.readCard8();
    javaStart += 1;
    byte[] pad7 = in.readPad(2);
    javaStart += 2;
    List<Byte> attributes = in.readChar(javaStart - length);
    javaBuilder.context(context);
    javaBuilder.stringLen(stringLen);
    javaBuilder.pool(pool);
    javaBuilder.rule(rule);
    javaBuilder.attributes(attributes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    out.writeCard32(stringLen);
    out.writeCard8(pool);
    out.writeCard8(rule);
    out.writePad(2);
    out.writeChar(attributes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * attributes.size();
  }

  public static class PrintSetAttributesBuilder {
    public int getSize() {
      return 16 + 1 * attributes.size();
    }
  }
}
