package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetScreenSaverReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  private short timeout;

  private short interval;

  private byte preferBlanking;

  private byte allowExposures;

  public static GetScreenSaverReply readGetScreenSaverReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetScreenSaverReply.GetScreenSaverReplyBuilder javaBuilder = GetScreenSaverReply.builder();
    int length = in.readCard32();
    short timeout = in.readCard16();
    short interval = in.readCard16();
    byte preferBlanking = in.readByte();
    byte allowExposures = in.readByte();
    byte[] pad8 = in.readPad(18);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timeout(timeout);
    javaBuilder.interval(interval);
    javaBuilder.preferBlanking(preferBlanking);
    javaBuilder.allowExposures(allowExposures);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(timeout);
    out.writeCard16(interval);
    out.writeByte(preferBlanking);
    out.writeByte(allowExposures);
    out.writePad(18);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetScreenSaverReplyBuilder {
    public GetScreenSaverReply.GetScreenSaverReplyBuilder preferBlanking(Blanking preferBlanking) {
      this.preferBlanking = (byte) preferBlanking.getValue();
      return this;
    }

    public GetScreenSaverReply.GetScreenSaverReplyBuilder preferBlanking(byte preferBlanking) {
      this.preferBlanking = preferBlanking;
      return this;
    }

    public GetScreenSaverReply.GetScreenSaverReplyBuilder allowExposures(Exposures allowExposures) {
      this.allowExposures = (byte) allowExposures.getValue();
      return this;
    }

    public GetScreenSaverReply.GetScreenSaverReplyBuilder allowExposures(byte allowExposures) {
      this.allowExposures = allowExposures;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
