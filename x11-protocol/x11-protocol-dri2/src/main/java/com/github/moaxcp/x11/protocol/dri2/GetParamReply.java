package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetParamReply implements XReply {
  public static final String PLUGIN_NAME = "dri2";

  private boolean paramRecognized;

  private short sequenceNumber;

  private int valueHi;

  private int valueLo;

  public static GetParamReply readGetParamReply(byte paramRecognized, short sequenceNumber,
      X11Input in) throws IOException {
    GetParamReply.GetParamReplyBuilder javaBuilder = GetParamReply.builder();
    int length = in.readCard32();
    int valueHi = in.readCard32();
    int valueLo = in.readCard32();
    in.readPad(16);
    javaBuilder.paramRecognized(paramRecognized > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.valueHi(valueHi);
    javaBuilder.valueLo(valueLo);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(paramRecognized);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(valueHi);
    out.writeCard32(valueLo);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetParamReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
