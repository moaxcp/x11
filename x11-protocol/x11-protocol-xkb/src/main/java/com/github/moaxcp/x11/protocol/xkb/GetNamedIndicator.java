package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetNamedIndicator implements TwoWayRequest<GetNamedIndicatorReply> {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 15;

  private short deviceSpec;

  private short ledClass;

  private short ledID;

  private int indicator;

  public XReplyFunction<GetNamedIndicatorReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetNamedIndicatorReply.readGetNamedIndicatorReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetNamedIndicator readGetNamedIndicator(X11Input in) throws IOException {
    GetNamedIndicator.GetNamedIndicatorBuilder javaBuilder = GetNamedIndicator.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    byte[] pad6 = in.readPad(2);
    int indicator = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    javaBuilder.indicator(indicator);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
    out.writePad(2);
    out.writeCard32(indicator);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetNamedIndicatorBuilder {
    public GetNamedIndicator.GetNamedIndicatorBuilder ledClass(LedClass ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public GetNamedIndicator.GetNamedIndicatorBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
