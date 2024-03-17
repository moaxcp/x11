package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InputSelected implements TwoWayRequest<InputSelectedReply> {
  public static final String PLUGIN_NAME = "shape";

  public static final byte OPCODE = 7;

  private int destinationWindow;

  public XReplyFunction<InputSelectedReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> InputSelectedReply.readInputSelectedReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static InputSelected readInputSelected(X11Input in) throws IOException {
    InputSelected.InputSelectedBuilder javaBuilder = InputSelected.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int destinationWindow = in.readCard32();
    javaBuilder.destinationWindow(destinationWindow);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(destinationWindow);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class InputSelectedBuilder {
    public int getSize() {
      return 8;
    }
  }
}
