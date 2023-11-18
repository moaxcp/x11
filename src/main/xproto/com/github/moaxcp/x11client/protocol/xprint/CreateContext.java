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
public class CreateContext implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 2;

  private int contextId;

  @NonNull
  private List<Byte> printerName;

  @NonNull
  private List<Byte> locale;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateContext readCreateContext(X11Input in) throws IOException {
    CreateContext.CreateContextBuilder javaBuilder = CreateContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextId = in.readCard32();
    int printerNameLen = in.readCard32();
    int localeLen = in.readCard32();
    List<Byte> printerName = in.readChar((int) (Integer.toUnsignedLong(printerNameLen)));
    List<Byte> locale = in.readChar((int) (Integer.toUnsignedLong(localeLen)));
    javaBuilder.contextId(contextId);
    javaBuilder.printerName(printerName);
    javaBuilder.locale(locale);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class CreateContextBuilder {
    public int getSize() {
      return 16 + 1 * printerName.size() + 1 * locale.size();
    }
  }
}
