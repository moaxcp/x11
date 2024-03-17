package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetPointerControlReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  private short accelerationNumerator;

  private short accelerationDenominator;

  private short threshold;

  public static GetPointerControlReply readGetPointerControlReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetPointerControlReply.GetPointerControlReplyBuilder javaBuilder = GetPointerControlReply.builder();
    int length = in.readCard32();
    short accelerationNumerator = in.readCard16();
    short accelerationDenominator = in.readCard16();
    short threshold = in.readCard16();
    byte[] pad7 = in.readPad(18);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.accelerationNumerator(accelerationNumerator);
    javaBuilder.accelerationDenominator(accelerationDenominator);
    javaBuilder.threshold(threshold);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(accelerationNumerator);
    out.writeCard16(accelerationDenominator);
    out.writeCard16(threshold);
    out.writePad(18);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetPointerControlReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
