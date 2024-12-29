package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class PrintSetAttributes implements OneWayRequest {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 18;

  private int context;

  private int stringLen;

  private byte pool;

  private byte rule;

  @NonNull
  private ByteList attributes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintSetAttributes readPrintSetAttributes(X11Input in) throws IOException {
    PrintSetAttributes.PrintSetAttributesBuilder javaBuilder = PrintSetAttributes.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
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
    ByteList attributes = in.readChar(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.context(context);
    javaBuilder.stringLen(stringLen);
    javaBuilder.pool(pool);
    javaBuilder.rule(rule);
    javaBuilder.attributes(attributes.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintSetAttributesBuilder {
    public int getSize() {
      return 16 + 1 * attributes.size();
    }
  }
}
