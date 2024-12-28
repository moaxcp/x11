package com.github.moaxcp.x11.protocol.xinput;

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
public class XIListPropertiesReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private short sequenceNumber;

  @NonNull
  private List<Integer> properties;

  public static XIListPropertiesReply readXIListPropertiesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    XIListPropertiesReply.XIListPropertiesReplyBuilder javaBuilder = XIListPropertiesReply.builder();
    int length = in.readCard32();
    short numProperties = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<Integer> properties = in.readCard32(Short.toUnsignedInt(numProperties));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.properties(properties);
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
    out.writeCard32(getLength());
    short numProperties = (short) properties.size();
    out.writeCard16(numProperties);
    out.writePad(22);
    out.writeCard32(properties);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * properties.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIListPropertiesReplyBuilder {
    public int getSize() {
      return 32 + 4 * properties.size();
    }
  }
}
