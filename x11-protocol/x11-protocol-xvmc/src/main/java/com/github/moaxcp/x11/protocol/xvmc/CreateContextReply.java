package com.github.moaxcp.x11.protocol.xvmc;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateContextReply implements XReply {
  public static final String PLUGIN_NAME = "xvmc";

  private short sequenceNumber;

  private short widthActual;

  private short heightActual;

  private int flagsReturn;

  @NonNull
  private List<Integer> privData;

  public static CreateContextReply readCreateContextReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    CreateContextReply.CreateContextReplyBuilder javaBuilder = CreateContextReply.builder();
    int length = in.readCard32();
    short widthActual = in.readCard16();
    short heightActual = in.readCard16();
    int flagsReturn = in.readCard32();
    byte[] pad7 = in.readPad(20);
    List<Integer> privData = in.readCard32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.widthActual(widthActual);
    javaBuilder.heightActual(heightActual);
    javaBuilder.flagsReturn(flagsReturn);
    javaBuilder.privData(privData);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    int length = privData.size();
    out.writeCard32(getLength());
    out.writeCard16(widthActual);
    out.writeCard16(heightActual);
    out.writeCard32(flagsReturn);
    out.writePad(20);
    out.writeCard32(privData);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 36 + 4 * privData.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateContextReplyBuilder {
    public int getSize() {
      return 36 + 4 * privData.size();
    }
  }
}
