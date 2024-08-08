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
public class CreateContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 2;

  private int contextId;

  @NonNull
  private ByteList printerName;

  @NonNull
  private ByteList locale;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateContext readCreateContext(X11Input in) throws IOException {
    CreateContext.CreateContextBuilder javaBuilder = CreateContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextId = in.readCard32();
    int printerNameLen = in.readCard32();
    int localeLen = in.readCard32();
    ByteList printerName = in.readChar((int) (Integer.toUnsignedLong(printerNameLen)));
    ByteList locale = in.readChar((int) (Integer.toUnsignedLong(localeLen)));
    javaBuilder.contextId(contextId);
    javaBuilder.printerName(printerName.toImmutable());
    javaBuilder.locale(locale.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextId);
    int printerNameLen = printerName.size();
    out.writeCard32(printerNameLen);
    int localeLen = locale.size();
    out.writeCard32(localeLen);
    out.writeChar(printerName);
    out.writeChar(locale);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * printerName.size() + 1 * locale.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateContextBuilder {
    public int getSize() {
      return 16 + 1 * printerName.size() + 1 * locale.size();
    }
  }
}
