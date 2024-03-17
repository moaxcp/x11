package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompareCursor implements TwoWayRequest<CompareCursorReply> {
  public static final String PLUGIN_NAME = "xtest";

  public static final byte OPCODE = 1;

  private int window;

  private int cursor;

  public XReplyFunction<CompareCursorReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CompareCursorReply.readCompareCursorReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CompareCursor readCompareCursor(X11Input in) throws IOException {
    CompareCursor.CompareCursorBuilder javaBuilder = CompareCursor.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int cursor = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.cursor(cursor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(cursor);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CompareCursorBuilder {
    public int getSize() {
      return 12;
    }
  }
}
